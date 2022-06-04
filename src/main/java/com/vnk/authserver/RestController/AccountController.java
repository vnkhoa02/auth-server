package com.vnk.authserver.RestController;

import com.vnk.authserver.Dto.AccountDto;
import com.vnk.authserver.Service.AccountService;
import com.vnk.authserver.Util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Constants.BASE_URL + "accounts")
@Api(tags = {"Accounts Service"})
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
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "401");
    }

    @DeleteMapping("/ban")
    public void banAccount(@RequestParam String id) {
        accountService.banAccount(id);
    }

    @PatchMapping("/active")
    public void activeAccount(@RequestParam String id) {
        accountService.activeAccount(id);
    }
}