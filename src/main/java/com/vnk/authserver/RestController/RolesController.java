package com.vnk.authserver.RestController;

import com.vnk.authserver.Dto.RolesDto;
import com.vnk.authserver.Entity.Roles;
import com.vnk.authserver.Service.RolesService;
import com.vnk.authserver.Util.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.BASE_URL + "/roles")
@Api(tags = {"Roles Services"})
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

    @ApiOperation(value = "Add permission to role")
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

    @ApiOperation(value = "Remove permission from role")
    @DeleteMapping("/remove-permission")
    public void removePermission(@RequestParam long roleId, @RequestParam long permissionId) {
        rolesService.removePermission(roleId, permissionId);
    }

    @ApiOperation(value = "Add role to account")
    @PutMapping("/add-roles")
    public void addRole(@RequestParam long accountId, @RequestParam long roleId){
        rolesService.addRole(accountId, roleId);
    }
}
