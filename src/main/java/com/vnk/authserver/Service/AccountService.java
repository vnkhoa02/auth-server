package com.vnk.authserver.Service;

import com.vnk.authserver.Auth.AuthenticationRequest;
import com.vnk.authserver.Auth.AuthenticationResponse;
import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Repository.AccountRepository;
import com.vnk.authserver.Repository.RolesRepository;
import com.vnk.authserver.Util.JwtUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    RolesRepository rolesRepo;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void create(AccountDto accountDto) {
        if (accountDto.getPassword() == null || accountDto.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username & Password invalid!");
        }
        if (accountRepo.getByUsername(accountDto.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account existed!");
        } else {
            Account account = new Account();
            account.setUsername(accountDto.getUsername());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setRoleId(rolesRepo.getByName("User").get().getId());
            account.setStatus(1);
            accountRepo.save(account);
        }
    }

    public String login(AuthenticationRequest request) {
        Account account = accountRepo.getByUsername(request.getUsername());
        if (account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            final String accessToken = jwtUtil.generateCustomToken(account);
            return new AuthenticationResponse(accessToken).getAccessToken();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public String getInfo(String token) {
        return jwtUtil.extractInfo(token);
    }

    @Transactional
    public void banAccount(long id) {
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
    public void activeAccount(long id) {
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