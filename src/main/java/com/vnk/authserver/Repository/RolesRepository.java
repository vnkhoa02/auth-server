package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query(value = "select * from Roles r where r.status = 1", nativeQuery = true)
    List<Roles> findAll();

    @Query(value = "select * from Roles r where r.status = 1 and r.name = ?", nativeQuery = true)
    Optional<Roles> getByName(String name);

    Optional<Roles> getRolesById(Long aLong);
}