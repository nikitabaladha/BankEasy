package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    
    List<Transaction> getAllTransactionsByUserId(UUID userId);
    
    Transaction getTransactionByTransactionId(UUID transactionId);
    
    List<Transaction> getTransactionsByType(UUID userId, TransactionType transactionType); 
}