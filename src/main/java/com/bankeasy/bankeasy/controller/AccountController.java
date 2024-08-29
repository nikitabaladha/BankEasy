package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.AccountValidator;
import com.bankeasy.bankeasy.reqres.AccountCreateRequest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Account>> createAccount(@RequestBody AccountCreateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Account createdAccount = accountService.createAccount(user, request);

            return new ResponseEntity<>(new ApiResponse<>(false, "Account created successfully.", createdAccount), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Account>> updateAccount(@Valid @RequestBody AccountValidator request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                StringBuilder errorMessage = new StringBuilder();
                result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage.toString().trim(), null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Account updatedAccount = accountService.updateAccountByUserId(userId, request.getBalance());

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

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteAccount() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Account account = accountService.getAccountByUserId(userId);
            if (account == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            accountService.deleteAccount(account);

            return new ResponseEntity<>(new ApiResponse<>(false, "Account deleted successfully.", "Deleted"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to delete account: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
