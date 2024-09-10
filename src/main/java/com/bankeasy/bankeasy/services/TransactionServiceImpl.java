package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.TransactionDao;
import com.bankeasy.bankeasy.entities.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
 
}
