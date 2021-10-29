package com.vnk.authserver.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RolesDto {
    private String name;

    @JsonIgnore
    private long account_id;

    @JsonIgnore
    private long permission_id;
}