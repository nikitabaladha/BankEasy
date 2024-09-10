//package com.bankeasy.bankeasy.controller;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bankeasy.bankeasy.entities.Beneficiary;
//import com.bankeasy.bankeasy.entities.User;
//import com.bankeasy.bankeasy.reqres.ApiResponse;
//import com.bankeasy.bankeasy.services.AccountService;
//import com.bankeasy.bankeasy.services.BeneficiaryService;
//import com.bankeasy.bankeasy.services.UserService;
//import com.bankeasy.bankeasy.validators.BeneficiaryUpdateValidator;
//import com.bankeasy.bankeasy.validators.BeneficiaryValidator;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/beneficiaries")
//public class BeneficiaryController {
//
//    @Autowired
//    private BeneficiaryService beneficiaryService;
//
//    @Autowired
//    private UserService userService;
//    
//    @Autowired
//    private AccountService accountService;
//
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<Beneficiary>> createBeneficiary(@Valid @RequestBody BeneficiaryValidator request, BindingResult result) {
//        try {
//        	if (result.hasErrors()) { 
//                String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
//                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage, null), HttpStatus.BAD_REQUEST);
//            }
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userId = (String) authentication.getPrincipal(); 
//            User user = userService.findById(UUID.fromString(userId));
//            
//         // Check if the account number exists
//            if (accountService.findByAccountNumber(request.getAccountNumber()).isEmpty()) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Sorry, this account number does not exist.", null), HttpStatus.BAD_REQUEST);
//            }
//
//            if (user == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
//            }
//
//            Beneficiary beneficiary = beneficiaryService.create(user, request.getName(), request.getBankName(), request.getAccountNumber(), request.getIfscCode());
//           
//            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary created successfully.", beneficiary), HttpStatus.CREATED);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
//    @PutMapping("/update/{beneficiaryId}")
//    public ResponseEntity<ApiResponse<Beneficiary>> updateBeneficiary(
//            @PathVariable UUID beneficiaryId,
//            @Valid @RequestBody BeneficiaryUpdateValidator request, 
//            BindingResult result) {
//        
//        try {
//            
//            if (result.hasErrors()) {
//                String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
//                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage, null), HttpStatus.BAD_REQUEST);
//            }
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userIdStr = (String) authentication.getPrincipal();
//            UUID userId = UUID.fromString(userIdStr);
//            User user = userService.findById(userId);
//            
//            if (user == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
//            }
//
//            Beneficiary existingBeneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
//            
//            if (existingBeneficiary == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
//            }
//
//            if (request.getName() != null) existingBeneficiary.setName(request.getName());
//            if (request.getBankName() != null) existingBeneficiary.setBankName(request.getBankName());
//            if (request.getAccountNumber() != null) existingBeneficiary.setAccountNumber(request.getAccountNumber());
//            if (request.getIfscCode() != null) existingBeneficiary.setIfscCode(request.getIfscCode());
//
//            Beneficiary updatedBeneficiary = beneficiaryService.updateBeneficiaryById(existingBeneficiary);
//
//            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary updated successfully.", updatedBeneficiary), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
//    
//    @GetMapping("/get/{beneficiaryId}")
//    public ResponseEntity<ApiResponse<Beneficiary>> getBeneficiary(@PathVariable UUID beneficiaryId) {
//        try {
//            // Retrieve the beneficiary using the ID from the path
//            Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
//
//            if (beneficiary == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
//            }
//
//            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary retrieved successfully.", beneficiary), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
//    
//    @GetMapping("/get-all")
//    public ResponseEntity<ApiResponse<List<Beneficiary>>> getAllBeneficiaries() {
//        try {
//            // Get the authenticated user's ID from the security context
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userIdStr = (String) authentication.getPrincipal();
//            UUID userId = UUID.fromString(userIdStr);
//
//            // Retrieve the user to ensure they exist
//            User user = userService.findById(userId);
//            
//            if (user == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
//            }
//
//            // Fetch all active beneficiaries associated with the user
//            List<Beneficiary> beneficiaries = beneficiaryService.getActiveBeneficiariesByUserId(userId);
//
//            // Return the list of beneficiaries if found
//            return new ResponseEntity<>(new ApiResponse<>(false, "Active beneficiaries retrieved successfully.", beneficiaries), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve beneficiaries: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    
//    @DeleteMapping("/delete/{beneficiaryId}")
//    public ResponseEntity<ApiResponse<Beneficiary>> softDeleteBeneficiary(@PathVariable UUID beneficiaryId) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userIdStr = (String) authentication.getPrincipal();
//            UUID userId = UUID.fromString(userIdStr);
//            
//            User user = userService.findById(userId);
//            
//            if (user == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
//            }
//
//            Beneficiary existingBeneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
//            
//            if (existingBeneficiary == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
//            }
//
//            Beneficiary updatedBeneficiary = beneficiaryService.softDeleteBeneficiaryById(beneficiaryId);
//            
//            if (updatedBeneficiary == null) {
//                return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update beneficiary status.", null), HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//
//            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary status updated to INACTIVE.", updatedBeneficiary), HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update beneficiary status: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
//

package com.bankeasy.bankeasy.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService;
import com.bankeasy.bankeasy.services.BeneficiaryService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.BeneficiaryUpdateValidator;
import com.bankeasy.bankeasy.validators.BeneficiaryValidator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Beneficiary>> createBeneficiary(@Valid @RequestBody BeneficiaryValidator request, BindingResult result) {
        try {
        	if (result.hasErrors()) { 
                String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
                return new ResponseEntity<>(new ApiResponse<>(true, errorMessage, null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal(); 
            User user = userService.findById(UUID.fromString(userId));
            
         // Check if the account number exists
            if (accountService.findByAccountNumber(request.getAccountNumber()).isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Sorry, this account number does not exist.", null), HttpStatus.BAD_REQUEST);
            }

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Beneficiary beneficiary = beneficiaryService.create(user, request.getName(), request.getBankName(), request.getAccountNumber(), request.getIfscCode());
           
            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary created successfully.", beneficiary), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{beneficiaryId}")
    public ResponseEntity<ApiResponse<Beneficiary>> updateBeneficiary(
            @PathVariable UUID beneficiaryId,
            @Valid @RequestBody BeneficiaryUpdateValidator request, 
            BindingResult result) {
        
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

            Beneficiary existingBeneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
            
            if (existingBeneficiary == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
            }

            if (request.getName() != null) existingBeneficiary.setName(request.getName());
            if (request.getBankName() != null) existingBeneficiary.setBankName(request.getBankName());
            if (request.getAccountNumber() != null) existingBeneficiary.setAccountNumber(request.getAccountNumber());
            if (request.getIfscCode() != null) existingBeneficiary.setIfscCode(request.getIfscCode());

            Beneficiary updatedBeneficiary = beneficiaryService.updateBeneficiaryById(existingBeneficiary);

            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary updated successfully.", updatedBeneficiary), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/get/{beneficiaryId}")
    public ResponseEntity<ApiResponse<Beneficiary>> getBeneficiary(@PathVariable UUID beneficiaryId) {
        try {
            // Retrieve the beneficiary using the ID from the path
            Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);

            if (beneficiary == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary retrieved successfully.", beneficiary), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve Beneficiary: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<Beneficiary>>> getAllBeneficiaries() {
        try {
            // Get the authenticated user's ID from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            // Retrieve the user to ensure they exist
            User user = userService.findById(userId);
            
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            // Fetch all active beneficiaries associated with the user
            List<Beneficiary> beneficiaries = beneficiaryService.getActiveBeneficiariesByUserId(userId);

            // Return the list of beneficiaries if found
            return new ResponseEntity<>(new ApiResponse<>(false, "Active beneficiaries retrieved successfully.", beneficiaries), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve beneficiaries: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{beneficiaryId}")
    public ResponseEntity<ApiResponse<Beneficiary>> softDeleteBeneficiary(@PathVariable UUID beneficiaryId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);
            
            User user = userService.findById(userId);
            
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Beneficiary existingBeneficiary = beneficiaryService.getBeneficiaryById(beneficiaryId);
            
            if (existingBeneficiary == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Beneficiary not found.", null), HttpStatus.NOT_FOUND);
            }

            Beneficiary updatedBeneficiary = beneficiaryService.softDeleteBeneficiaryById(beneficiaryId);
            
            if (updatedBeneficiary == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update beneficiary status.", null), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Beneficiary status updated to INACTIVE.", updatedBeneficiary), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update beneficiary status: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


