package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.TransactionService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.TransactionValidator;

import jakarta.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
//            transactions.forEach(transaction -> Hibernate.initialize(transaction.getTransfer()));
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
   
}
