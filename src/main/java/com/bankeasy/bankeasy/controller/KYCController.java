package com.bankeasy.bankeasy.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.KYCService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.KYCValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/kyc")
public class KYCController {

    @Autowired
    private KYCService kycService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<KYC>> createKYC(@Valid @RequestBody KYCValidator request, BindingResult result) {
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

            KYC kyc = kycService.createKYC(user, request.getDocumentType(), request.getDocumentNumber());
            return new ResponseEntity<>(new ApiResponse<>(false, "KYC created successfully.", kyc), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<KYC>> updateKYC(@Valid @RequestBody KYCValidator request, BindingResult result) {
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

            KYC updatedKYC = kycService.updateKYCByUserId(userId, request.getDocumentType(), request.getDocumentNumber());

            if (updatedKYC == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "KYC not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC updated successfully.", updatedKYC), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<KYC>>> getAllKYCs() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);
            
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            List<KYC> kycs = kycService.getAllKYCsByUserId(userId);
            return new ResponseEntity<>(new ApiResponse<>(false, "KYCs retrieved successfully.", kycs), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve KYCs: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
       
        
    }
}
