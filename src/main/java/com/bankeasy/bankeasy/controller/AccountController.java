package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService;
import com.bankeasy.bankeasy.services.KYCService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.AccountUpdateValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private KYCService kycService;

 // ++++++++++ADMIN APIS++++++++++
    
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<Account>> createAccountForUser(@PathVariable UUID userId) {
        try {
            // Get the currently authenticated user (the admin)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserIdStr = (String) authentication.getPrincipal();
            User authenticatedUser = userService.findById(UUID.fromString(authenticatedUserIdStr));

            // Check if the authenticated user is an Admin
            if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can create accounts.", null), HttpStatus.FORBIDDEN);
            }

            // Find the user for whom the account is being created
            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "User not found.", null), HttpStatus.NOT_FOUND);
            }
            
            // Update the user's status to "Approved"
            userService.updateUserStatus(userId, User.UserStatus.Approved);
            
            // Update the KYC status to "Approved"
            kycService.updateKYCStatus(userId, KYC.VerificationStatus.Approved);

            // Create the account for the user
            Account createdAccount = accountService.createAccount(user);

            return new ResponseEntity<>(new ApiResponse<>(false, "Account created successfully.", createdAccount), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts() {
        try {
            // Get the currently authenticated admin user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            User admin = userService.findById(UUID.fromString(userIdStr));

            // Check if the authenticated user is an Admin
            if (admin == null || !admin.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view all accounts.", null), HttpStatus.FORBIDDEN);
            }

            // Get all accounts
            List<Account> allAccounts = accountService.getAllAccounts();
            
            List<Account> filteredAccounts = new ArrayList<>();

            // Filter accounts based on user status
            for (Account account : allAccounts) {
                User accountUser = userService.findById(account.getUserId());
                if (accountUser != null && User.UserStatus.Approved.equals(accountUser.getStatus())) {
                    filteredAccounts.add(account);
                }
            }

            // Remove admin's own account from the list
            filteredAccounts.removeIf(account -> account.getUserId().equals(admin.getId()));

            return new ResponseEntity<>(new ApiResponse<>(false, "Accounts retrieved successfully.", filteredAccounts), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve accounts: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   
    @PutMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<Account>> deleteAccount(@PathVariable UUID userId) {
        try {
            // Get the authenticated user (to check if it's an admin)
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserIdStr = (String) authentication.getPrincipal();
            User authenticatedUser = userService.findById(UUID.fromString(authenticatedUserIdStr));

            // Check if the authenticated user is an Admin
            if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can reject accounts.", null), HttpStatus.FORBIDDEN);
            }

            // Find the user whose account needs to be rejected
            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "User not found.", null), HttpStatus.NOT_FOUND);
            }

            // Update the user's status to "Rejected"
            userService.updateUserStatus(userId, User.UserStatus.Rejected);
            
            // Update the KYC status to "Rejected"
            kycService.updateKYCStatus(userId, KYC.VerificationStatus.Rejected);

            // Optionally: Return the updated account information if necessary
            Account account = accountService.findByUserId(userId);
            return new ResponseEntity<>(new ApiResponse<>(false, "Account status updated to Rejected.", account), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to reject account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/rejected-all")
    public ResponseEntity<ApiResponse<List<Account>>> getAllRejectedAccounts() {
        try {
            // Get the currently authenticated admin user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            User admin = userService.findById(UUID.fromString(userIdStr));

            // Check if the authenticated user is an Admin
            if (admin == null || !admin.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view rejected accounts.", null), HttpStatus.FORBIDDEN);
            }

            // Fetch users with status 'Rejected' and their accounts
            List<User> rejectedUsers = userService.findByStatus(User.UserStatus.Rejected);
            List<Account> rejectedAccounts = new ArrayList<>();
            
            for (User rejectedUser : rejectedUsers) {
                Account account = accountService.findByUserId(rejectedUser.getId());
                if (account != null) {
                    rejectedAccounts.add(account);
                }
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Rejected accounts retrieved successfully.", rejectedAccounts), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve rejected accounts: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//++++++++++USER APIS++++++++++
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Account>> updateAccount(@Valid @RequestBody AccountUpdateValidator request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage, null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }
            
            BigDecimal newBalance = request.getBalance(); 

          
            Account updatedAccount = accountService.updateAccountByUserId(userId, newBalance);

            if (updatedAccount == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Account updated successfully.", updatedAccount), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<Account>> getMyAccount() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();

            Account account = accountService.getAccountByUserId(UUID.fromString(userId));
            if (account == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Account retrieved successfully.", account), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/find/{accountNumber}")
    public ResponseEntity<ApiResponse<Account>> findAccountByAccountNumber(@PathVariable String accountNumber) {
        try {
            Optional<Account> accountOptional = accountService.findByAccountNumber(accountNumber);

            if (accountOptional.isPresent()) {
                return new ResponseEntity<>(new ApiResponse<>(false, "Account retrieved successfully.", accountOptional.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found.", null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

