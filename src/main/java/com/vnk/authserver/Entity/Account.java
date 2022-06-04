package com.vnk.authserver.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account")
@Data
public class Account extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    private String username;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "password", nullable = false)
    @NotNull
    private String password;

    @Column(name = "role_id")
    private Long roleId;

}
