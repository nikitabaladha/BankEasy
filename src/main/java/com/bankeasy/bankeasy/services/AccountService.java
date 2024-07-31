package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Account createAccount(User user, String accountNumber);
    Account updateAccount(UUID accountId, BigDecimal newBalance, String status);
    Account getAccountByUserId(UUID userId);
    void deleteAccount(UUID accountId);
	
   }
