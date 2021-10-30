package com.vnk.authserver.Service;


import com.vnk.authserver.Dto.RolesDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import com.vnk.authserver.Repository.AccountRepository;
import com.vnk.authserver.Repository.PermissionRepository;
import com.vnk.authserver.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    RolesRepository repo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    AccountRepository accountRepo;

    @Transactional
    public ResponseEntity<?> create(RolesDto rolesDto) {
        Optional<Roles> checkExists = repo.getByName(rolesDto.getName());
        if (!checkExists.isPresent()) {
            Roles roles = new Roles();
            roles.setName(rolesDto.getName());
            roles.setStatus(1);
            repo.save(roles);
        }
        return new ResponseEntity(String.format("Role %s existed!", checkExists.get().getName()), HttpStatus.BAD_REQUEST);
    }

    public List<Roles> findAll() {
        return repo.findAll();
    }

    @Transactional
    public void update(RolesDto rolesDto) {
        Optional<Roles> roles = repo.getByName(rolesDto.getName());
        if (roles.isPresent()) {
            roles.get().setName(rolesDto.getName());
            repo.save(roles.get());
        }
    }

    @Transactional
    public void delete(Long id) {
        Optional<Roles> roles = repo.getRolesById(id);
        if (roles.isPresent()) {
            roles.get().setStatus(0);
            repo.save(roles.get());
        }
    }

    @Transactional
    public void addPermission(Long roleId, Long permissionId) {
        Optional<Roles> roles = repo.getRolesById(roleId);
        Permission permission = permissionRepo.getById(permissionId);
        roles.get().getPermissionList().add(permission);
        repo.save(roles.get());
    }

    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        Optional<Roles> roles = repo.getRolesById(roleId);
        Permission permission = permissionRepo.getById(permissionId);
        roles.get().removePermission(permission);
        repo.save(roles.get());
    }

    public Roles getById(Long aLong) {
        return repo.getRolesById(aLong).get();
    }

    @Transactional
    public void addRole(Long id, Long roleId) {
        Account account = accountRepo.getById(id);
        Roles roles = getById(roleId);
        account.setRole(roles);
        accountRepo.save(account);
    }
}