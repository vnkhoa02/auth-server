package com.vnk.authserver.Repository;

import com.vnk.authserver.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select * from accounts a where a.username = ?", nativeQuery = true)
    public Account getByUsername(String username);
}