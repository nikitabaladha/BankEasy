package com.bankeasy.bankeasy.services;

import java.math.BigDecimal;

import java.util.List;
import java.util.UUID;
import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
import com.bankeasy.bankeasy.entities.User;

public interface TransactionService {

    Transaction createTransaction(User user, BigDecimal amount, TransactionType transactionType, String description);
    
    List<Transaction> getTransactionsByUserId(UUID userId);
    
    Transaction getTransactionById(UUID transactionId);
    
    Transaction updateTransactionById(UUID transactionId, BigDecimal newAmount, TransactionType newTransactionType, String newDescription);
    
    void deleteTransaction(UUID transactionId);
}



