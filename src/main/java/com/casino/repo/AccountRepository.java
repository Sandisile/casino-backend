package com.casino.repo;

import com.casino.model.Account;
import com.casino.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("From Account acc where lower(acc.player.username) = lower(:username) ORDER BY created DESC")
    Set<Transaction> findTransactionsDescByPlayerUsernameIgnoreCase(String username);
}
