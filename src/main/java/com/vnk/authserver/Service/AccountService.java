package com.vnk.authserver.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.vnk.authserver.Auth.AuthenticationRequest;
import com.vnk.authserver.Auth.AuthenticationResponse;
import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import com.vnk.authserver.Repository.AccountRepository;
import com.vnk.authserver.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    RolesService rolesService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private int LOGIN_FAIL_MAX = 4;

    private LoadingCache<String, Integer> requestCountsPerIpAddress;

    public AccountService() {
        super();
        requestCountsPerIpAddress = CacheBuilder.newBuilder().
                expireAfterWrite(300, TimeUnit.SECONDS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    @Transactional
    public void create(AuthenticationRequest auth) {
        if (auth.getPassword() == null || auth.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username & Password invalid!");
        }
        if (accountRepo.getByUsername(auth.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account existed!");
        } else {
            Account account = new Account();
            account.setUuid(UUID.nameUUIDFromBytes(auth.getUsername().getBytes(StandardCharsets.UTF_8)).toString());
            account.setUsername(auth.getUsername());
            account.setPassword(passwordEncoder.encode(auth.getPassword()));
            account.setRoleId(1L);
            account.setStatus(1);
            accountRepo.save(account);
        }
    }

    public String login(AuthenticationRequest request) {
        Account account = accountRepo.getByUsername(request.getUsername());
        if (account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            AccountDto accountDto = new AccountDto();
            Optional<Roles> roles = rolesService.getById(account.getRoleId());
            accountDto.setUsername(account.getUsername());
            accountDto.setId(account.getId());
            if (roles.isPresent()) {
                accountDto.setRole(roles.get().getName());
                List<String> list = new ArrayList<>();
                for (Permission p : roles.get().getPermissionList()) {
                    list.add(p.getName());
                }
                accountDto.setPermissions(list);
            }
            final String accessToken = jwtUtil.generateCustomToken(accountDto);
            return new AuthenticationResponse(accessToken).getAccessToken();
        }
        if (maxFailPerMin(getClientIP(httpServletRequest))) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Username & Password!");
    }

    private boolean maxFailPerMin(String clientIpAddress) {
        int requests = 0;
        try {
            requests = requestCountsPerIpAddress.get(clientIpAddress);
            if (requests >= LOGIN_FAIL_MAX) {
                requestCountsPerIpAddress.put(clientIpAddress, requests);
                return true;
            }
        } catch (ExecutionException e) {
            requests = 0;
        }
        requests++;
        requestCountsPerIpAddress.put(clientIpAddress, requests);
        return false;
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    public AccountDto getInfo(String token) {
        return jwtUtil.extractInfo(token);
    }

    @Transactional
    public void banAccount(String id) {
        if (accountRepo.existsById(id)) {
            Account account = accountRepo.getById(id);
            account.setStatus(0);
            accountRepo.save(account);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public void activeAccount(String id) {
        if (accountRepo.existsById(id)) {
            Account account = accountRepo.getById(id);
            account.setStatus(1);
            accountRepo.save(account);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public Account getByUsername(String username) {
        return accountRepo.getByUsername(username);
    }
}