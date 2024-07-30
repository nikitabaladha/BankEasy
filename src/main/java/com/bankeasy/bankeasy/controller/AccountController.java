package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.services.AccountService;
import com.bankeasy.bankeasy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestParam String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        User user = userService.findById(UUID.fromString(userId));
        
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        Account account = accountService.createAccount(user, accountNumber);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID accountId, @RequestParam BigDecimal balance, @RequestParam String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        
        Account account = accountService.getAccountByNumber(accountId.toString());
        if (account == null || !account.getUser().getId().equals(UUID.fromString(userId))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        account = accountService.updateAccount(accountId, balance, status);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountByNumber(@PathVariable String accountNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        
        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null || !account.getUser().getId().equals(UUID.fromString(userId))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        
        Account account = accountService.getAccountByNumber(accountId.toString());
        if (account == null || !account.getUser().getId().equals(UUID.fromString(userId))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
