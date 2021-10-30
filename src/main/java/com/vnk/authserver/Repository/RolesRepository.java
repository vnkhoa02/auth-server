package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> getByName(String name);

    Optional<Roles> getRolesById(Long aLong);
}