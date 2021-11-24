package com.vnk.authserver.Dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}