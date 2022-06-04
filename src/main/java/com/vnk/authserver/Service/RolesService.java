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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {

    @Autowired
    public RolesRepository repo;

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
        } else {
            return new ResponseEntity(String.format("Role %s existed!", checkExists.get().getName()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Unknown!", HttpStatus.BAD_REQUEST);
    }

    public List<Roles> findAll() {
        return repo.findAll();
    }

    @Transactional
    public void update(RolesDto rolesDto) {
        Optional<Roles> roles = repo.getRolesById(rolesDto.getId());
        if (roles.isPresent()) {
            roles.get().setName(rolesDto.getName());
            repo.save(roles.get());
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void delete(RolesDto rolesDto) {
        Optional<Roles> roles = repo.getRolesById(rolesDto.getId());
        if (roles.isPresent()) {
            roles.get().setStatus(0);
            repo.save(roles.get());
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void addPermission(Long roleId, Long permissionId) {
        Optional<Roles> roles = repo.getRolesById(roleId);
        if (roles.isPresent()) {
            Optional<Permission> permission = permissionRepo.getPermissionById(permissionId);
            if (permission.isPresent()) {
                roles.get().getPermissionList().add(permission.get());
                repo.save(roles.get());
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        Optional<Roles> roles = repo.getRolesById(roleId);
        if (roles.isPresent()) {
            Optional<Permission> permission = permissionRepo.getPermissionById(permissionId);
            if (permission.isPresent()) {
                roles.get().removePermission(permission.get());
                repo.save(roles.get());
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<Roles> getById(Long aLong) {
        return repo.getRolesById(aLong);
    }

    @Transactional
    public void addRole(Long id, Long roleId) {
        Optional<Roles> roles = repo.getRolesById(roleId);
        if (roles.isPresent()) {
            Optional<Account> account = accountRepo.getAccountById(id);
            if (account.isPresent()) {
                account.get().setRoleId(roleId);
                accountRepo.save(account.get());
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}