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
       
        return new ResponseEntity<>(new ApiResponse<>(false, "Account created successfully.", account), HttpStatus.CREATED);
    }
   
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Account>> updateAccount(@RequestBody Map<String, String> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdStr = (String) authentication.getPrincipal();
        UUID userId = UUID.fromString(userIdStr);

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
        }

        try {
            BigDecimal newBalance = new BigDecimal(request.get("balance"));

            Account updatedAccount = accountService.updateAccountByUserId(userId, newBalance);

            if (updatedAccount == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Account updated successfully.", updatedAccount), HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Invalid balance format.", null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update account.", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 @GetMapping("/get")
    public ResponseEntity<ApiResponse<Account>> getMyAccount() {
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = (String) authentication.getPrincipal();

      
        Account account = accountService.getAccountByUserId(UUID.fromString(userId));
        if (account == null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
        }

      
        return new ResponseEntity<>(new ApiResponse<>(false, "Account retrieved successfully.", account), HttpStatus.OK);
    }

 @DeleteMapping("/delete")
 public ResponseEntity<ApiResponse<String>> deleteAccount() {
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

     accountService.deleteAccount(userId);

     return new ResponseEntity<>(new ApiResponse<>(false, "Account status updated to Deleted.", "Deleted"), HttpStatus.OK);
 }

}
