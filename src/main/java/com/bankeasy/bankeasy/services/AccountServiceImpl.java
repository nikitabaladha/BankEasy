package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public Account createAccount(User user, String accountNumber) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        Account account = new Account(user, accountNumber, BigDecimal.ZERO, "Active");
        return accountDao.save(account);
    }

    @Override
    @Transactional
    public Account updateAccountByUserId(UUID userId, BigDecimal newBalance) {
        Account account = accountDao.findByUserId(userId);
        if (account == null) {
            throw new NullPointerException("Account not found for user " + userId);
        }
        account.setBalance(newBalance);
        return accountDao.save(account);
    }

    @Override
    @Transactional
    public Account getAccountByUserId(UUID userId) {
        return accountDao.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteAccount(UUID userId) {
        Account account = accountDao.findByUserId(userId);
        if (account == null) {
            throw new NullPointerException("Account not found for user " + userId);
        }
        account.setAccountStatus("Deleted");
        accountDao.save(account);
    }
}