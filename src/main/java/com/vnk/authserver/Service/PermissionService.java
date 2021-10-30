package com.vnk.authserver.Service;

import com.vnk.authserver.Dto.PermissionDto;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> create(PermissionDto permissionDto) {
        Optional<Permission> checkExists = repo.getPermissionByName(permissionDto.getName());
        if (!checkExists.isPresent()) {
            Permission permission = new Permission();
            permission.setName(permissionDto.getName());
            permission.setStatus(1);
            repo.save(permission);
        }else {
            return new ResponseEntity(String.format("Permission %s existed!", checkExists.get().getName()), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Transactional
    public void update(PermissionDto permissionDto) {
        Optional<Permission> permission = repo.getPermissionById(permissionDto.getId());
        if (permission.isPresent()) {
            permission.get().setName(permissionDto.getName());
            repo.save(permission.get());
        }
    }

    @Transactional
    public void delete(PermissionDto permissionDto) {
        Optional<Permission> permission = repo.getPermissionById(permissionDto.getId());
        if (permission.isPresent()) {
            permission.get().setStatus(0);
            repo.save(permission.get());
        }
    }


}