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
import org.springframework.http.HttpStatus;
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

    @GetMapping("/test")
    public String hello(){
        return "hi";
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDto accountDto){
        return accountService.create(accountDto);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        if(accountService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword())){
            final Account account = accountService.getByUsername(authenticationRequest.getUsername());

            final String accessToken = jwtUtil.generateCustomToken(account);

            return ResponseEntity.ok(new AuthenticationResponse(accessToken).getAccessToken());
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    @DeleteMapping("/{id}")
    public void banAccount(@PathVariable long id) throws NotFoundException {
        accountService.banAccount(id);
    }

    @PatchMapping("/{id}")
    public void activeAccount(@PathVariable long id) throws NotFoundException {
        accountService.activeAccount(id);
    }

    @PutMapping("/{id}/{roleId}")
    public void addRole(@PathVariable long id, @PathVariable long roleId){
        rolesService.addRole(id, roleId);
    }


}