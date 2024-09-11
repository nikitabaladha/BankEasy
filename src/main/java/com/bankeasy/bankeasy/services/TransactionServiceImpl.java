//package com.bankeasy.bankeasy.services;
//
//import com.bankeasy.bankeasy.dao.TransactionDao;
//import com.bankeasy.bankeasy.entities.Transaction;
//import com.bankeasy.bankeasy.entities.Transaction.TransactionStatus;
//import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class TransactionServiceImpl implements TransactionService {
//
//    @Autowired
//    private TransactionDao transactionDao;
//
//    @Override
//    public List<Transaction> getAllTransactionsByUserId(UUID userId) {
//        return transactionDao.findAllByUserId(userId);
//    }
//    
//
//    @Override
//    public Transaction getTransactionByTransactionId(UUID transactionId) {
//    	return transactionDao.findById(transactionId)
//    			.orElseThrow(() -> new RuntimeException("Transaction not found for ID: " + transactionId));
//    }
//
//
////	@Override
////	public List<Transaction> searchTransactions(UUID userId, TransactionStatus status, TransactionType transactionType) {
////		return transactionDao.findByUserIdAndStatusAndTransactionTypeAndCreatedAtBetween(userId, status, transactionType);
////	}
//}

package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.TransactionDao;
import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionStatus;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public List<Transaction> getAllTransactionsByUserId(UUID userId) {
        return transactionDao.findAllByUserId(userId);
    }
    

    @Override
    public Transaction getTransactionByTransactionId(UUID transactionId) {
    	return transactionDao.findById(transactionId)
    			.orElseThrow(() -> new RuntimeException("Transaction not found for ID: " + transactionId));
    }
    
 // ... other methods

    @Override
    public List<Transaction> getTransactionsByType(UUID userId, TransactionType transactionType) {
        List<Transaction> allTransactions = transactionDao.findAllByUserId(userId);
        List<Transaction> filteredTransactions = allTransactions.stream()
                .filter(transaction -> transaction.getTransactionType().equals(transactionType))
                .collect(Collectors.toList());
        return filteredTransactions;
    }
}