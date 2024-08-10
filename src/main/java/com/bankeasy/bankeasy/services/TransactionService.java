package com.bankeasy.bankeasy.services;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
import com.bankeasy.bankeasy.entities.User;

public interface TransactionService {

    Transaction createTransaction(User user, BigDecimal amount, TransactionType transactionType, String description);
    
    List<Transaction> getAllTransactionsByUserId(UUID userId);
    
    Transaction getTransactionByTransactionId(UUID transactionId);
  
    Transaction updateTransactionByTransactionId(UUID transactionId, BigDecimal newAmount, TransactionType newTransactionType, String newDescription);

}



