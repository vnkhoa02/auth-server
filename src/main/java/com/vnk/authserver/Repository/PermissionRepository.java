package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> getPermissionByName(String name);

    Optional<Permission> getPermissionById(Long id);
}