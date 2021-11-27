package com.vnk.authserver.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private Long id;
    private String username;
    private String role;
    private List<String> permissions;
}