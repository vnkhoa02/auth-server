package com.vnk.authserver.Auth;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AuthenticationRequest implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
