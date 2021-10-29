package com.vnk.authserver.Service;
import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Repository.AccountRepository;
import com.vnk.authserver.Repository.RolesRepository;
import com.vnk.authserver.Util.JwtUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
            System.out.println("info missing !");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(accountRepo.getByUsername(accountDto.getUsername()) != null){
            System.out.println("account exists!");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else {
            Account account = new Account();
            account.setUsername(accountDto.getUsername());
            account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
            account.setRole(rolesRepo.getByName("User"));
            account.setStatus(1);
            accountRepo.save(account);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    public boolean login(String username, String password){
        Account account = getByUsername(username);
        if(account != null){ ;
            return passwordEncoder.matches(password, account.getPassword());
        }
        return false;
    }

    @Transactional
    public void banAccount(long id) throws NotFoundException {
        if(accountRepo.existsById(id)){
            Account account = accountRepo.getById(id);
            account.setStatus(0);
            accountRepo.save(account);
        }else {
            throw new NotFoundException("account not exists!");
        }
    }

    @Transactional
    public void activeAccount(long id) throws NotFoundException {
        if(accountRepo.existsById(id)){
            Account account = accountRepo.getById(id);
            account.setStatus(1);
            accountRepo.save(account);
        }else {
            throw new NotFoundException("account not exists!");
        }
    }

    @Query(value = "select * from accounts a where a.username = ?", nativeQuery = true)
    public Account getByUsername(String username) {
        return accountRepo.getByUsername(username);
    }

}