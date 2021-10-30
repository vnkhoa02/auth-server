package com.vnk.authserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AccountDto {
    private String username;
    private String password;
}