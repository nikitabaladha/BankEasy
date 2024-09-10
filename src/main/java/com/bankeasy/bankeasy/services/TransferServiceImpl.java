package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.dao.BeneficiaryDao;
import com.bankeasy.bankeasy.dao.TransactionDao;
import com.bankeasy.bankeasy.dao.TransferDao;
import com.bankeasy.bankeasy.dao.UserDao;
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
        Transfer transfer = new Transfer(user, beneficiaryId, amount, remark);
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
