package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.TransactionDao;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
import com.bankeasy.bankeasy.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction createTransaction(User user, BigDecimal amount, TransactionType transactionType, String description) {
        Transaction transaction = new Transaction(user, amount, transactionType, description);
        return transactionDao.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionsByUserId(UUID userId) {
        return transactionDao.findAllByUserId(userId);
    }

    
  @Override
  public Transaction getTransactionByTransactionId(UUID transactionId) {
      return transactionDao.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found for ID: " + transactionId));
 }
    
    

    @Override
    public Transaction updateTransactionByTransactionId(UUID transactionId, BigDecimal newAmount, TransactionType newTransactionType, String newDescription) {
        Transaction transaction = getTransactionByTransactionId(transactionId);

        transaction.setAmount(newAmount);
        transaction.setTransactionType(newTransactionType);
        transaction.setDescription(newDescription);

        return transactionDao.save(transaction);
    }
    
//
//    @Override
//    public void deleteTransaction(UUID transactionId) {
//        Transaction transaction = getTransactionById(transactionId);
//        transactionDao.delete(transaction);
//    }
}
