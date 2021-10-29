package com.vnk.authserver.Service;

import com.vnk.authserver.Dto.PermissionDto;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    PermissionRepository repo;

    public List<Permission> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Permission create(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setName(permissionDto.getName());
        permission.setStatus(0);
        return repo.save(permission);
    }

    @Transactional
    public Permission update(long id, PermissionDto permissionDto) {
        Permission permission = repo.getById(id);
        if (permissionDto.getName() != null) {
            permission.setName(permissionDto.getName());
        }
        return repo.save(permission);
    }

    @Transactional
    public void delete(long id) {
        Permission permission = repo.getById(id);
        permission.setStatus(0);
    }


}