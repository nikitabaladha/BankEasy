package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Transfer;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService; 
import com.bankeasy.bankeasy.services.TransferService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.TransferValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Transfer>> createTransfer(@Valid @RequestBody TransferValidator request, BindingResult result) {
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage, null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }
  
            Account account = accountService.findByUserId(user.getId());

            if (account == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Account not found for the user.", null), HttpStatus.NOT_FOUND);
            }
         
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Sorry, insufficient balance.", null), HttpStatus.BAD_REQUEST);
            }

            Transfer transfer = transferService.createTransfer(user, request.getBeneficiaryId(), request.getAmount(), request.getRemark());

            return new ResponseEntity<>(new ApiResponse<>(false, "Transfer created successfully.", transfer), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Transfer already exists or invalid data.", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create transfer: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
