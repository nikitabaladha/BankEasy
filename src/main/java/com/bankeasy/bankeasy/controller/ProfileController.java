package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.ProfileService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.ProfileValidator;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")

public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Profile>> createProfile(@Valid @RequestBody ProfileValidator request, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));
            return new ResponseEntity<>(new ApiResponse<>(true, errorMessage.toString().trim(), null), HttpStatus.BAD_REQUEST);
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Profile profile = profileService.createProfile(user, request.getName(), request.getAddress(), request.getPhoneNumber());
            return new ResponseEntity<>(new ApiResponse<>(false, "Profile created successfully.", profile), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create profile. " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@Valid @RequestBody ProfileValidator request, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append(" "));
            return new ResponseEntity<>(new ApiResponse<>(true, errorMessage.toString().trim(), null), HttpStatus.BAD_REQUEST);
        }

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            User user = userService.findById(userId);

            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null),
                        HttpStatus.UNAUTHORIZED);
            }

            Profile updatedProfile = profileService.updateProfileByUserId(userId, request.getName(), request.getAddress(), request.getPhoneNumber());

            if (updatedProfile == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Profile not found for the user.", null),
                        HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Profile updated successfully.", updatedProfile),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update profile. " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get")
    public ResponseEntity<ApiResponse<Profile>> getProfile() {
        try {
        	
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            UUID userId = UUID.fromString(userIdStr);

            Profile profile = profileService.getProfileByUserId(userId);

            if (profile == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Profile not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Profile retrieved successfully.", profile), HttpStatus.OK);
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve profile. " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}