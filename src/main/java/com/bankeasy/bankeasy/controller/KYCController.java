package com.bankeasy.bankeasy.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.KYCService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.KYCUpdateValidator;
import com.bankeasy.bankeasy.validators.KYCValidator;

import jakarta.validation.Valid;

import com.bankeasy.bankeasy.services.FileStorageService;

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
        	
        	System.out.println("Received createKYC request with data: " + request); 
        	System.out.println("Uploaded file: " + file);
        	   
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
            
          KYC existingKyc = kycService.findByUserId(user.getId());

          if (existingKyc != null) {
              if (existingKyc.getVerified() == KYC.VerificationStatus.Pending || existingKyc.getVerified() == KYC.VerificationStatus.Approved) {
                  // If KYC exists and is pending or approved, do not allow user to create a new KYC
                  return new ResponseEntity<>(new ApiResponse<>(true, "KYC is already pending or approved for this user.", null), HttpStatus.CONFLICT);
              } else if (existingKyc.getVerified() == KYC.VerificationStatus.Rejected) {
                  // If KYC exists and is rejected, allow user to create a new KYC
                  // Keep the old KYC as well as the new one
                  String fileUrl = null;
                  if (file != null && !file.isEmpty()) {
                      // Store the uploaded file
                      String fileName = fileStorageService.storeFile(file);
                      fileUrl = "/" + fileName;
                  } else {
                      return new ResponseEntity<>(new ApiResponse<>(true, "File is required.", null), HttpStatus.BAD_REQUEST);
                  }

                  KYC kyc = kycService.createKYC(user, request.getDocumentType(), request.getDocumentNumber(), fileUrl);

                  return new ResponseEntity<>(new ApiResponse<>(false, "KYC created successfully.", kyc), HttpStatus.CREATED);
              }
          } else {
              String fileUrl = null;
              if (file != null && !file.isEmpty()) {
                  // Store the uploaded file
                  String fileName = fileStorageService.storeFile(file);
                  fileUrl = "/" + fileName;
              } else {
                  return new ResponseEntity<>(new ApiResponse<>(true, "File is required.", null), HttpStatus.BAD_REQUEST);
              }

              KYC kyc = kycService.createKYC(user, request.getDocumentType(), request.getDocumentNumber(), fileUrl);

              return new ResponseEntity<>(new ApiResponse<>(false, "KYC created successfully.", kyc), HttpStatus.CREATED);
        }
          } catch (DataIntegrityViolationException e) {
            
            return new ResponseEntity<>(new ApiResponse<>(true, "KYC already exists for this user.", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        
      }
        return ResponseEntity.badRequest().body(new ApiResponse<>(true, "Failed to create KYC.", null));
		 
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
            String fileUrl = existingKYC.getDocumentUrl(); 
            if (file != null && !file.isEmpty()) {
                // Store the new file
                String fileName = fileStorageService.storeFile(file);
                fileUrl = "/uploads/" + fileName; 
            }
            existingKYC.setDocumentUrl(fileUrl); 

            KYC updatedKYC = kycService.updateKYC(existingKYC);

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC updated successfully.", updatedKYC), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get-approved")
    public ResponseEntity<ApiResponse<KYC>> getApprovedKYC() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            KYC kyc = kycService.getApprovedKYCByUserId(userId);

            if (kyc == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Approved KYC not found for the user.", null), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC retrieved successfully.", kyc), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<KYC>> getKYC() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            KYC kyc = kycService.getKYCByUserId(userId);

            if (kyc == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Approved KYC not found for the user.", null), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC retrieved successfully.", kyc), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
//    Admin 
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<KYC>> getKYC(@PathVariable UUID userId) {
        try {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            User authenticatedUser = userService.findById(UUID.fromString(userIdStr));

            // Check if the authenticated user is an Admin
            if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view KYC.", null), HttpStatus.FORBIDDEN);
            }

            // Get the profile of the user by userId
            KYC kyc = kycService.getKYCByUserId(userId);

            // Check if the profile exists
            if (kyc == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "KYC not found.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "KYC retrieved successfully.", kyc), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve KYC: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
}
    
 



