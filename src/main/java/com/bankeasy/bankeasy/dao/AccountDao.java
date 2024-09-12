package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountDao extends JpaRepository<Account, UUID> {
    Account findByUserId(UUID userId);
    
//    New
    Optional<Account> findByAccountNumber(String accountNumber);
   
}


