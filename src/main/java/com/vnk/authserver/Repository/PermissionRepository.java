package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Permission;
import com.vnk.authserver.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = "select * from Permission p where p.status = 1", nativeQuery = true)
    List<Permission> findAll();

    Optional<Permission> getPermissionByName(String name);

    Optional<Permission> getPermissionById(Long id);
}