package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;

import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService;
import com.bankeasy.bankeasy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody Map<String, String> request) {
        String accountNumber = request.get("accountNumber");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();
        User user = userService.findById(UUID.fromString(userId));

        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
        }

        Account account = accountService.createAccount(user, accountNumber);
        // Remove sensitive information from the Account object if needed before sending
        return new ResponseEntity<>(new ApiResponse<>(false, "Account created successfully.", account), HttpStatus.CREATED);
    }
   

//    @PutMapping("/update/{accountId}")
//    public ResponseEntity<Account> updateAccount(@PathVariable UUID accountId, @RequestParam BigDecimal balance, @RequestParam String status) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = (String) authentication.getPrincipal();
//        
//        Account account = accountService.getAccountByNumber(accountId.toString());
//        if (account == null || !account.getUserId().equals(UUID.fromString(userId))) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        
//        account = accountService.updateAccount(accountId, balance, status);
//        return new ResponseEntity<>(account, HttpStatus.OK);
//    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<Account>> getMyAccount() {
        // Get the authenticated user's ID from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();

        // Fetch the account associated with the authenticated user
        Account account = accountService.getAccountByUserId(UUID.fromString(userId));
        if (account == null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
        }

        // Prepare and return the response
        return new ResponseEntity<>(new ApiResponse<>(false, "Account retrieved successfully.", account), HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{accountId}")
//    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = (String) authentication.getPrincipal();
//        
//        Account account = accountService.getAccountByNumber(accountId.toString());
//        if (account == null || !account.getUserId().equals(UUID.fromString(userId))) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        
//        accountService.deleteAccount(accountId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
