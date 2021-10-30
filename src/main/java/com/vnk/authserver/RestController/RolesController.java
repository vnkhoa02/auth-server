package com.vnk.authserver.RestController;

import com.vnk.authserver.Dto.RolesDto;
import com.vnk.authserver.Entity.Roles;
import com.vnk.authserver.Service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    RolesService rolesService;

    @GetMapping("")
    public List<Roles> findAll() {
        return rolesService.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody RolesDto rolesDto) {
         return rolesService.create(rolesDto);
    }

    @PutMapping("/add-permission")
    public void addPermission(@RequestParam long roleId, @RequestParam long permissionId) {
        rolesService.addPermission(roleId, permissionId);
    }

    @PutMapping("")
    public void update(@RequestBody RolesDto rolesDto) {
         rolesService.update(rolesDto);
    }

    @DeleteMapping("")
    public void delete(@RequestBody RolesDto rolesDto) {
        rolesService.delete(rolesDto);
    }

    @DeleteMapping("/remove-permission")
    public void removePermission(@RequestParam long roleId, @RequestParam long permissionId) {
        rolesService.removePermission(roleId, permissionId);
    }
}
