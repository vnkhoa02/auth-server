package com.vnk.authserver.Service;


import com.vnk.authserver.Dto.RolesDto;
import com.vnk.authserver.Entity.Account;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import com.vnk.authserver.Repository.AccountRepository;
import com.vnk.authserver.Repository.PermissionRepository;
import com.vnk.authserver.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesService {

    @Autowired
    RolesRepository repo;

    @Autowired
    PermissionRepository permissionRepo;

    @Autowired
    AccountRepository accountRepo;

    @Transactional
    public Roles create(RolesDto rolesDto){
        Roles roles = new Roles();
        roles.setName(rolesDto.getName());
        if(accountRepo.existsById(rolesDto.getAccount_id())){
            roles.getAccountList().add(accountRepo.getById(rolesDto.getAccount_id()));
        }
        if (permissionRepo.existsById(rolesDto.getPermission_id())){
            roles.getPermissionList().add(permissionRepo.getById(rolesDto.getPermission_id()));
        }
        roles.setStatus(1);
        return repo.save(roles);
    }

    public List<Roles> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Roles update(long id, RolesDto rolesDto){
        Roles roles = repo.getById(id);
        if(rolesDto.getName() != null){
            roles.setName(rolesDto.getName());
        }
        return repo.save(roles);
    }

    @Transactional
    public void delete(long id){
        Roles roles = repo.getById(id);
        roles.setStatus(0);
        repo.save(roles);
    }

    @Transactional
    public void addPermission(long roleId, long permissionId){
        Roles roles = repo.getById(roleId);
        Permission permission = permissionRepo.getById(permissionId);
        roles.getPermissionList().add(permission);
        repo.save(roles);
    }

    @Transactional
    public void removePermission(long roleId, long permissionId){
        Roles roles = repo.getById(roleId);
        Permission permission = permissionRepo.getById(permissionId);
        roles.removePermission(permission);
        repo.save(roles);
    }

    public Roles getById(Long aLong) {
        return repo.getById(aLong);
    }

    @Transactional
    public void addRole(long id, long roleId){
        Account account = accountRepo.getById(id);
        Roles roles = getById(roleId);
        account.setRole(roles);
        accountRepo.save(account);
    }
}