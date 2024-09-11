//package com.bankeasy.bankeasy.services;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//import com.bankeasy.bankeasy.entities.Transaction;
//import com.bankeasy.bankeasy.entities.Transaction.TransactionStatus;
//import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
//
//public interface TransactionService {
//    
//    List<Transaction> getAllTransactionsByUserId(UUID userId);
//    
////    List<Transaction> searchTransactions(UUID userId, TransactionStatus status, TransactionType transactionType);
//    
//    Transaction getTransactionByTransactionId(UUID transactionId);
//}

package com.bankeasy.bankeasy.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionStatus;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;

public interface TransactionService {
    
    List<Transaction> getAllTransactionsByUserId(UUID userId);
    
    Transaction getTransactionByTransactionId(UUID transactionId);
    
    List<Transaction> getTransactionsByType(UUID userId, TransactionType transactionType); 
}