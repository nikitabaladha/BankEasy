package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.TransactionService;
import com.bankeasy.bankeasy.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;
    
//++++++++++ADMIN APIS+++++++++
    @GetMapping("/get-all/{userId}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactionsForAdmin(@PathVariable UUID userId) {
        try {
        	 // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
             User authenticatedUser = userService.findById(UUID.fromString(userIdStr));

            // Check if the authenticated user is an Admin
             if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("Admin")) {
                 return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view KYC.", null), HttpStatus.FORBIDDEN);
             }

            List<Transaction> transactions = transactionService.getAllTransactionsByUserId(userId);
            return new ResponseEntity<>(new ApiResponse<>(false, "Transactions retrieved successfully.", transactions), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve transactions: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
//++++++++++USER APIS++++++++++

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            List<Transaction> transactions = transactionService.getAllTransactionsByUserId(userId);
            return new ResponseEntity<>(new ApiResponse<>(false, "Transactions retrieved successfully.", transactions), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve transactions: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get/{transactionId}")
    public ResponseEntity<ApiResponse<Transaction>> getTransaction(@PathVariable UUID transactionId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);
            return new ResponseEntity<>(new ApiResponse<>(false, "Transaction retrieved successfully.", transaction), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve transaction: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     

    @GetMapping("/by-type/{transactionType}")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionsByType(@PathVariable String transactionType) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            // Convert the incoming transactionType to match the enum case
            Transaction.TransactionType type;
            try {
                type = Transaction.TransactionType.valueOf(transactionType.substring(0, 1).toUpperCase() + transactionType.substring(1).toLowerCase());
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Invalid transaction type.", null), HttpStatus.BAD_REQUEST);
            }

            // Fetch transactions and filter by the correct transaction type
            List<Transaction> transactions = transactionService.getAllTransactionsByUserId(userId);
            transactions.removeIf(transaction -> !transaction.getTransactionType().equals(type));

            return new ResponseEntity<>(new ApiResponse<>(false, "Transactions retrieved successfully.", transactions), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve transactions: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



