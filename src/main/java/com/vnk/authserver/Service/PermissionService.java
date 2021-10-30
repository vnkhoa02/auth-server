package com.vnk.authserver.Service;

import com.vnk.authserver.Dto.PermissionDto;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    PermissionRepository repo;

    public List<Permission> findAll() {
        return repo.findAll();
    }

    @Transactional
    public void create(PermissionDto permissionDto) {
        if (!repo.getPermissionByName(permissionDto.getName()).isPresent()) {
            Permission permission = new Permission();
            permission.setName(permissionDto.getName());
            permission.setStatus(0);
            repo.save(permission);
        }
    }

    @Transactional
    public void update(PermissionDto permissionDto) {
        Optional<Permission> permission = repo.getPermissionByName(permissionDto.getName());
        if (permission.isPresent()) {
            permission.get().setName(permissionDto.getName());
            repo.save(permission.get());
        }
    }

    @Transactional
    public void delete(long id) {
        Optional<Permission> permission = repo.getPermissionById(id);
        if (permission.isPresent()) {
            permission.get().setStatus(0);
            repo.save(permission.get());
        }
    }


}