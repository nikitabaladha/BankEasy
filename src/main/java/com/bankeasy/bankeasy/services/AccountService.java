package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import java.math.BigDecimal;
import java.util.UUID;

public interface AccountService {
	
    Account createAccount(User user, String accountNumber);
    
    Account updateAccountByUserId(UUID userId, BigDecimal newBalance);
    
    Account getAccountByUserId(UUID userId);
    
    void deleteAccount(UUID userId);
}
