package com.vnk.authserver.Dto;

import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String role;
    private List<String> permissions;
}