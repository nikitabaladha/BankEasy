package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.dao.BeneficiaryDao;
import com.bankeasy.bankeasy.dao.TransactionDao;
import com.bankeasy.bankeasy.dao.TransferDao;
import com.bankeasy.bankeasy.dao.UserDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
import com.bankeasy.bankeasy.entities.Transfer;
import com.bankeasy.bankeasy.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferDao transferDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private BeneficiaryDao beneficiaryDao;

    @Autowired
    private UserDao userDao; 
    
    @Override
    public Transfer createTransfer(User user, UUID beneficiaryId, BigDecimal amount, String remark) {
        // Fetch beneficiary details
        Beneficiary beneficiary = beneficiaryDao.findById(beneficiaryId)
            .orElseThrow(() -> new RuntimeException("Beneficiary not found for ID: " + beneficiaryId));
          
        // Create and save the transfer
        Transfer transfer = new Transfer(user, beneficiaryId, amount, remark, Transfer.TransferStatus.Active);
        Transfer savedTransfer = transferDao.save(transfer);

        // Create debit transaction for the sender
        String debitDescription = "Transfer to " + beneficiary.getName();
        Transaction debitTransaction = new Transaction(user, amount, Transaction.TransactionType.Debit, debitDescription, savedTransfer);
        transactionDao.save(debitTransaction);

        // Use the userId from the Beneficiary to get the User
        UUID beneficiaryUserId = beneficiary.getUserId();
        User beneficiaryUser = userDao.findById(beneficiaryUserId)
            .orElseThrow(() -> new RuntimeException("User not found for Beneficiary User ID: " + beneficiaryUserId));

        // Create credit transaction for the beneficiary
        String creditDescription = "Transfer from " + user.getFirstName();
        Transaction creditTransaction = new Transaction(beneficiaryUser, amount, Transaction.TransactionType.Credit, creditDescription, savedTransfer);
        transactionDao.save(creditTransaction);

        return savedTransfer;
    }

}

//package com.bankeasy.bankeasy.services;
//
//import com.bankeasy.bankeasy.dao.AccountDao;
//import com.bankeasy.bankeasy.dao.BeneficiaryDao;
//import com.bankeasy.bankeasy.dao.TransactionDao;
//import com.bankeasy.bankeasy.dao.TransferDao;
//import com.bankeasy.bankeasy.dao.UserDao;
//import com.bankeasy.bankeasy.entities.Account;
//import com.bankeasy.bankeasy.entities.Beneficiary;
//import com.bankeasy.bankeasy.entities.Transaction;
//import com.bankeasy.bankeasy.entities.Transaction.TransactionType;
//import com.bankeasy.bankeasy.entities.Transfer;
//import com.bankeasy.bankeasy.entities.User;
//import com.bankeasy.bankeasy.reqres.ApiResponse;
//
//import jakarta.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Service
//public class TransferServiceImpl implements TransferService {
//
//    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);
//
//    @Autowired
//    private TransferDao transferDao;
//
//    @Autowired
//    private TransactionDao transactionDao;
//
//    @Autowired
//    private AccountDao accountDao;
//
//    @Autowired
//    private BeneficiaryDao beneficiaryDao;
//
//    @Autowired
//    private UserDao userDao; 
//    
//    @Override
//    @Transactional // Ensure this method runs in a transaction
//    public Transfer createTransfer(User user, UUID beneficiaryId, BigDecimal amount, String remark) {
//        // Fetch beneficiary details
//        Beneficiary beneficiary = beneficiaryDao.findById(beneficiaryId)
//                .orElseThrow(() -> new RuntimeException("Beneficiary not found for ID: " + beneficiaryId));
//        
//        // Create and save the transfer
//        Transfer transfer = new Transfer(user, beneficiaryId, amount, remark, Transfer.TransferStatus.Active);
//        Transfer savedTransfer = transferDao.save(transfer);
//        logger.info("Transfer created and saved: {}", savedTransfer);
//
//        // Create debit transaction for the sender
//        String debitDescription = "Transfer to " + beneficiary.getName();
//        Transaction debitTransaction = new Transaction(user, amount, Transaction.TransactionType.Debit, debitDescription, savedTransfer);
//        transactionDao.save(debitTransaction);
//        logger.info("Debit transaction created and saved: {}", debitTransaction);
//
//        // Update sender's account balance
//        Account senderAccount = accountDao.findByUserId(user.getId());
//        if (senderAccount == null) {
//            throw new RuntimeException("Sender's account not found for User ID: " + user.getId());
//        }
//        BigDecimal senderInitialBalance = senderAccount.getBalance();
//        BigDecimal newSenderBalance = senderInitialBalance.subtract(amount);
//        senderAccount.setBalance(newSenderBalance);
//        accountDao.save(senderAccount); // Save the updated account
//        logger.info("Sender's account balance updated: {} -> {}", senderAccount.getId(), newSenderBalance);
//        logger.info("Transaction committed successfully"); // Verify transaction commit
//
//        // Update receiver's account balance
//        UUID beneficiaryUserId = beneficiary.getUserId();
//        User beneficiaryUser = userDao.findById(beneficiaryUserId)
//                .orElseThrow(() -> new RuntimeException("User not found for Beneficiary User ID: " + beneficiaryUserId));
//        Account beneficiaryAccount = accountDao.findByUserId(beneficiaryUserId);
//        if (beneficiaryAccount == null) {
//            throw new RuntimeException("Beneficiary's account not found for User ID: " + beneficiaryUserId);
//        }
//        BigDecimal receiverInitialBalance = beneficiaryAccount.getBalance();
//        BigDecimal newReceiverBalance = receiverInitialBalance.add(amount);
//        beneficiaryAccount.setBalance(newReceiverBalance);
//        accountDao.save(beneficiaryAccount); // Save the updated account
//        logger.info("Beneficiary's account balance updated: {} -> {}", beneficiaryAccount.getId(), newReceiverBalance);
//        logger.info("Transaction committed successfully"); // Verify transaction commit
//
//        // Create credit transaction for the beneficiary
//        String creditDescription = "Transfer from " + user.getFirstName();
//        Transaction creditTransaction = new Transaction(beneficiaryUser, amount, Transaction.TransactionType.Credit, creditDescription, savedTransfer);
//        transactionDao.save(creditTransaction);
//        logger.info("Credit transaction created and saved: {}", creditTransaction);
//
//        return savedTransfer;
//    }	
//}
