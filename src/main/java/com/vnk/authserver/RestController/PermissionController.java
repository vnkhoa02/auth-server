package com.vnk.authserver.RestController;

import com.vnk.authserver.Dto.PermissionDto;
import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public List<Permission> findAll() {
        return permissionService.findAll();
    }

    @PostMapping("")
    public void create(@RequestBody PermissionDto permissionDto) {
        permissionService.create(permissionDto);
    }

    @PutMapping("")
    public void update(@RequestBody PermissionDto permissionDto) {
        permissionService.update(permissionDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        permissionService.delete(id);
    }
}