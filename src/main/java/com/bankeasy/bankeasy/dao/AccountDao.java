package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountDao extends JpaRepository<Account, UUID> {
    Account findByUserId(UUID userId);
    
    Optional<Account> findByAccountNumber(String accountNumber);
}


