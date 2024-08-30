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
import org.springframework.web.multipart.MultipartFile;

import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.KYCService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.KYCUpdateValidator;
import com.bankeasy.bankeasy.validators.KYCValidator;

import jakarta.validation.Valid;

import com.bankeasy.bankeasy.services.FileStorageService;

import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@RequestMapping("/api/kyc")
public class KYCController {

    private final KYCService kycService;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Autowired
    public KYCController(KYCService kycService, UserService userService, FileStorageService fileStorageService) {
        this.kycService = kycService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<KYC>> createKYC(
            @Valid @ModelAttribute KYCValidator request,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file
           ) {
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

            String fileUrl = null;
            if (file != null && !file.isEmpty()) {
                // Store the uploaded file
                String fileName = fileStorageService.storeFile(file);
                fileUrl = "/uploads/" + fileName;
            } else {
                return new ResponseEntity<>(new ApiResponse<>(true, "File is required.", null), HttpStatus.BAD_REQUEST);
            }

            // Create the KYC entry with the uploaded document URL
            KYC kyc = kycService.createKYC(user, request.getDocumentType(), request.getDocumentNumber(), fileUrl);

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC created successfully.", kyc), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<KYC>> updateKYC(
            @Valid @ModelAttribute KYCUpdateValidator request,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file) {
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

            KYC existingKYC = kycService.getKYCByUserId(userId);
            if (existingKYC == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "KYC not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            // Update fields if they are not null
            if (request.getDocumentType() != null) existingKYC.setDocumentType(request.getDocumentType());
            if (request.getDocumentNumber() != null) existingKYC.setDocumentNumber(request.getDocumentNumber());

            // Handle file upload if a new file is provided
            String fileUrl = existingKYC.getDocumentUrl(); // Keep the old URL if no new file is uploaded
            if (file != null && !file.isEmpty()) {
                // Store the new file
                String fileName = fileStorageService.storeFile(file);
                fileUrl = "/uploads/" + fileName; // Update with new file URL
            }
            existingKYC.setDocumentUrl(fileUrl); // Update KYC with the new file URL

            KYC updatedKYC = kycService.updateKYC(existingKYC);

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
