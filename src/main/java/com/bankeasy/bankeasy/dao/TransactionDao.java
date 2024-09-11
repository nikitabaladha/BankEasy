package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionStatus;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;


import java.util.List;
import java.util.UUID;

public interface TransactionDao extends JpaRepository<Transaction, UUID> {
	
    List<Transaction> findAllByUserId(UUID userId); 
	
//    List<Transaction> findByUserIdAndStatusAndTransactionTypeAndCreatedAtBetween(UUID userId, TransactionStatus status,TransactionType transactionType);
}