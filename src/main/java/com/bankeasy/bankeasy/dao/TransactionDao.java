package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionDao extends JpaRepository<Transaction, UUID> {

	
    List<Transaction> findByUserId(UUID userId);

   
}

