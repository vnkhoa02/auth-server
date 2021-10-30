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
    public ResponseEntity<?> create(AccountDto accountDto){
        if(accountDto.getPassword() == null || accountDto.getUsername() == null){
            return new ResponseEntity("Username and Password invalid", HttpStatus.BAD_REQUEST);
        }
        if(accountRepo.getByUsername(accountDto.getUsername()) != null){
            return new ResponseEntity("Account existed!", HttpStatus.BAD_REQUEST);
        }else {
            Account account = new Account();
            account.setUsername(accountDto.getUsername());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setRoleId(rolesRepo.getByName("User").get().getId());
            account.setStatus(1);
            accountRepo.save(account);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    public ResponseEntity<?> login(AuthenticationRequest request) {
        Account account = accountRepo.getByUsername(request.getUsername());
        if (account != null && passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            final String accessToken = jwtUtil.generateCustomToken(account);
            return ResponseEntity.ok(new AuthenticationResponse(accessToken).getAccessToken());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<?> banAccount(long id) throws NotFoundException {
        if (accountRepo.existsById(id)) {
            Account account = accountRepo.getById(id);
            account.setStatus(0);
            accountRepo.save(account);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Transactional
    public ResponseEntity<?> activeAccount(long id) throws NotFoundException {
        if(accountRepo.existsById(id)){
            Account account = accountRepo.getById(id);
            account.setStatus(1);
            accountRepo.save(account);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public Account getByUsername(String username) {
        return accountRepo.getByUsername(username);
    }
}