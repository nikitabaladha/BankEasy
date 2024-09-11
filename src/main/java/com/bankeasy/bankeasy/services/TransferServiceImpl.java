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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class TransferServiceImpl implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

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

        // Update sender's account balance
        Account senderAccount = accountDao.findByUserId(user.getId());
        BigDecimal originalSenderBalance = senderAccount.getBalance();
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));
        Account updatedSenderAccount = accountDao.save(senderAccount);

//        till this code worked
        // Log the balance update for the sender
        logger.info("Sender account balance updated. Original balance: {}, Updated balance: {}", 
            originalSenderBalance, updatedSenderAccount.getBalance());

        // Create debit transaction for the sender
        String debitDescription = "Transfer to " + beneficiary.getName();
        Transaction debitTransaction = new Transaction(user.getId(), amount, TransactionType.Debit, debitDescription, savedTransfer);
        transactionDao.save(debitTransaction);

        // Use the userId from the Beneficiary to get the User
        UUID beneficiaryUserId = beneficiary.getUserId();
        User beneficiaryUser = userDao.findById(beneficiaryUserId)
            .orElseThrow(() -> new RuntimeException("User not found for Beneficiary User ID: " + beneficiaryUserId));

     // Retrieve the correct account using the account number associated with the beneficiary
        String beneficiaryAccountNumber = beneficiary.getAccountNumber();
        Account beneficiaryAccount = accountDao.findByAccountNumber(beneficiaryAccountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found for account number: " + beneficiaryAccountNumber));


        // Update beneficiary's account balance
        BigDecimal originalBeneficiaryBalance = beneficiaryAccount.getBalance();
        beneficiaryAccount.setBalance(beneficiaryAccount.getBalance().add(amount));
        Account updatedBeneficiaryAccount = accountDao.save(beneficiaryAccount);
        
//        Problematic code end

        // Log the balance update for the beneficiary
        logger.info("Beneficiary account balance updated. Original balance: {}, Updated balance: {}", 
            originalBeneficiaryBalance, updatedBeneficiaryAccount.getBalance());

        // Create credit transaction for the beneficiary
        String creditDescription = "Transfer from " + user.getFirstName();
        Transaction creditTransaction = new Transaction(beneficiaryAccount.getUserId(), amount, TransactionType.Credit, creditDescription, savedTransfer);
        transactionDao.save(creditTransaction);

        return savedTransfer;
    }

}