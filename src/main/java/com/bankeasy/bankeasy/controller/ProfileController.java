package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.ProfileService;
import com.bankeasy.bankeasy.services.UserService;
import com.bankeasy.bankeasy.validators.ProfileUpdateValidator;
import com.bankeasy.bankeasy.validators.ProfileValidator;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

            Profile profile = profileService.createProfile(user, request.getFirstName(),request.getLastName(),request.getDateOfBirth(), request.getPhoneNumber(),request.getAddress(), request.getCity(),request.getState(),request.getZipCode(),  request.getCountry(), request.getMaritalStatus(),request.getOccupation(), request.getAccountType());
            
            return new ResponseEntity<>(new ApiResponse<>(false, "Profile created successfully.", profile), HttpStatus.CREATED);
           } catch (DataIntegrityViolationException e) {
          
            return new ResponseEntity<>(new ApiResponse<>(true, "Profile already exists for this user.", null), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create profile: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@Valid @RequestBody ProfileUpdateValidator request, BindingResult result) {
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

            // Retrieve existing profile
            Profile existingProfile = profileService.getProfileByUserId(userId);
            if (existingProfile == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Profile not found for the user.", null), HttpStatus.NOT_FOUND);
            }

            // Update only the fields that are not null in the request
            if (request.getFirstName() != null) existingProfile.setFirstName(request.getFirstName());
            if (request.getLastName() != null) existingProfile.setLastName(request.getLastName());
            if (request.getAddress() != null) existingProfile.setAddress(request.getAddress());
            if (request.getPhoneNumber() != null) existingProfile.setPhoneNumber(request.getPhoneNumber());
            if (request.getCity() != null) existingProfile.setCity(request.getCity());
            if (request.getState() != null) existingProfile.setState(request.getState());
            if (request.getZipCode() != null) existingProfile.setZipCode(request.getZipCode());
            if (request.getCountry() != null) existingProfile.setCountry(request.getCountry());
            if (request.getMaritalStatus() != null) existingProfile.setMaritalStatus(request.getMaritalStatus());
            if (request.getOccupation() != null) existingProfile.setOccupation(request.getOccupation());
            if (request.getDateOfBirth() != null) existingProfile.setDateOfBirth(request.getDateOfBirth());

            Profile updatedProfile = profileService.updateProfile(existingProfile);

            return new ResponseEntity<>(new ApiResponse<>(false, "Profile updated successfully.", updatedProfile), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update profile: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
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
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve profile: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
//    Admin Apis
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Profile>>> getAllProfiles() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userIdStr));

            if (user == null || !user.getRole().equals("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view all profiles.", null), HttpStatus.FORBIDDEN);
            }

            // Get all profiles
            List<Profile> profiles = profileService.getAllProfiles();

            // Filter out the admin's own profile
            profiles.removeIf(profile -> profile.getUserId().equals(user.getId()));

            return new ResponseEntity<>(new ApiResponse<>(false, "Profiles retrieved successfully.", profiles), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve profiles: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<Profile>> getProfile(@PathVariable UUID userId) {
        try {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userIdStr = (String) authentication.getPrincipal();
            User authenticatedUser = userService.findById(UUID.fromString(userIdStr));

            // Check if the authenticated user is an Admin
            if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("Admin")) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: Only admins can view profiles.", null), HttpStatus.FORBIDDEN);
            }

            // Get the profile of the user by userId
            Profile profile = profileService.getProfileByUserId(userId);

            // Check if the profile exists
            if (profile == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Profile not found.", null), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(new ApiResponse<>(false, "Profile retrieved successfully.", profile), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to retrieve Profile: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}


