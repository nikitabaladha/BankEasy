package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account createAccount(User user, String accountNumber) {
        Account account = new Account(user, accountNumber, BigDecimal.ZERO, "Active");
        return accountDao.save(account);
    }

    @Override
    public Account updateAccount(UUID accountId, BigDecimal newBalance, String status) {
        Account account = accountDao.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(newBalance);
        account.setAccountStatus(status);
       
        return accountDao.save(account);
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountDao.findByAccountNumber(accountNumber);
    }

    @Override
    public void deleteAccount(UUID accountId) {
        accountDao.deleteById(accountId);
    }
}
