package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query(value = "select * from roles r where r.name = ?", nativeQuery = true)
    public Roles getByName(String roleName);
}