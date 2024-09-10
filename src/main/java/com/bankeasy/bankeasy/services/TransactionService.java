package com.bankeasy.bankeasy.services;

import java.util.List;
import java.util.UUID;
import com.bankeasy.bankeasy.entities.Transaction;

public interface TransactionService {
    
    List<Transaction> getAllTransactionsByUserId(UUID userId);
    
    Transaction getTransactionByTransactionId(UUID transactionId);
}
