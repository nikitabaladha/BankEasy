package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.AccountCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Account createAccount(User user, AccountCreateRequest request) {
    	
        String accountNumber = AccountUtils.generateRandomAccountNumber();
        Account account = new Account(user, accountNumber, BigDecimal.ZERO, request.getAccountType());
        return accountDao.save(account);
    }
    

    @Override
    public Account updateAccountByUserId(UUID userId, BigDecimal newBalance) {
        Account account = accountDao.findByUserId(userId);
        if (account != null) {
            account.setBalance(newBalance);
            return accountDao.save(account);
        }
        return null; 
    }

    @Override
    public Account getAccountByUserId(UUID userId) {
        return accountDao.findByUserId(userId);
    }

    @Override
    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    @Override
    public Account findByUserId(UUID userId) {
        return accountDao.findByUserId(userId);
    }
}
