package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getByUsername(String username);

    Optional<Account> getAccountById(Long id);
}