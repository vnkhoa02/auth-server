package com.vnk.authserver.RestController;

import com.vnk.authserver.Auth.AuthenticationRequest;
import com.vnk.authserver.Auth.AuthenticationResponse;
import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Service.AccountService;
import com.vnk.authserver.Service.RolesService;
import com.vnk.authserver.Util.JwtUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RolesService rolesService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDto accountDto){
        return accountService.create(accountDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return accountService.login(authenticationRequest);
    }


    @DeleteMapping("/{id}")
    public void banAccount(@PathVariable long id) throws NotFoundException {
        accountService.banAccount(id);
    }

    @PatchMapping("/{id}")
    public void activeAccount(@PathVariable long id) throws NotFoundException {
        accountService.activeAccount(id);
    }

    @PutMapping("/addRoles")
    public void addRole(@RequestParam long id, @RequestParam long roleId){
        rolesService.addRole(id, roleId);
    }

}