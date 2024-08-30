package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
    Account createAccount(User user);
    Account updateAccountByUserId(UUID userId, BigDecimal newBalance);
    Account getAccountByUserId(UUID userId);
    void deleteAccount(Account account);
    Account findByUserId(UUID userId);
}
