package com.vnk.authserver.RestController;

import com.vnk.authserver.Auth.AuthenticationRequest;
import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
@Api(tags = {"Authentication & Authorization Services"})
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "Get current user's info")
    @GetMapping("/info")
    public AccountDto getInfo(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return accountService.getInfo(authorizationHeader);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "404");
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthenticationRequest auth) {
        accountService.create(auth);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return accountService.login(authenticationRequest);
    }

    @DeleteMapping("")
    public void banAccount(@RequestParam long id) {
        accountService.banAccount(id);
    }

    @PatchMapping("")
    public void activeAccount(@RequestParam long id) {
        accountService.activeAccount(id);
    }
}