package com.vnk.authserver.RestController;

import com.vnk.authserver.Auth.AuthenticationRequest;
import com.vnk.authserver.Service.AccountService;
import com.vnk.authserver.Util.Constants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(tags = {"Authentication Services"})
@RequestMapping(Constants.BASE_URL)
public class AuthController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return accountService.login(authenticationRequest);
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthenticationRequest auth) {
        accountService.create(auth);
    }
}
