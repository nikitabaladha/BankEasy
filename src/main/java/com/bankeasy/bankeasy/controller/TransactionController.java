package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Transaction;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.TransactionService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.TransactionValidator;

import jakarta.validation.Valid;
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

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@Valid @RequestBody TransactionValidator request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage.toString().trim(), null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Transaction transaction = transactionService.createTransaction(user, request.getAmount(), request.getTransactionType(), request.getDescription());
            return new ResponseEntity<>(new ApiResponse<>(false, "Transaction created successfully.", transaction), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create transaction: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<ApiResponse<Transaction>> updateTransaction(@PathVariable UUID transactionId,
                                                                      @Valid @RequestBody TransactionValidator transactionValidator, BindingResult result) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            if (result.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage.toString().trim(), null), HttpStatus.BAD_REQUEST);
            }

            Transaction updatedTransaction = transactionService.updateTransactionByTransactionId(
                    transactionId,
                    transactionValidator.getAmount(),
                    transactionValidator.getTransactionType(),
                    transactionValidator.getDescription()
            );

            return new ResponseEntity<>(new ApiResponse<>(false, "Transaction updated successfully.", updatedTransaction), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update transaction: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
