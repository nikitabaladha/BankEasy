package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.dao.ProfileDao;
import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;
    
    @Autowired
    private ProfileDao profileDao;
	
    @Override
    public Account createAccount(User user) {
        
        Profile profile = profileDao.findByUserId(user.getId());

        if (profile == null) {
            throw new RuntimeException("Profile not found for userId: " + user.getId());
        }

       String accountType = profile.getAccountType();

       String accountNumber = AccountUtils.generateRandomAccountNumber();

       Account account = new Account(user, accountNumber, BigDecimal.ZERO, accountType);

       return accountDao.save(account);
    }

    @Override
    public Account updateAccountByUserId(UUID userId, BigDecimal newBalance) {
        Account account = accountDao.findByUserId(userId);
        if (account != null) {
            if (newBalance != null) {
                account.setBalance(newBalance);
            }
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
    
//    New
    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountDao.findByAccountNumber(accountNumber);
    }
}
