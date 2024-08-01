package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.ProfileService;
import com.bankeasy.bankeasy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Profile>> createProfile(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String address = request.get("address");
            String phoneNumber = request.get("phoneNumber");
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = (String) authentication.getPrincipal();
            User user = userService.findById(UUID.fromString(userId));
            
            if (user == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
            }

            Profile profile = profileService.createProfile(user, name, address, phoneNumber);
            
            return new ResponseEntity<>(new ApiResponse<>(false, "Profile created successfully.", profile), HttpStatus.CREATED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to create profile. " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Profile>> updateProfile(@RequestBody Map<String, String> request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userIdStr = (String) authentication.getPrincipal();
        UUID userId = UUID.fromString(userIdStr);

        User user = userService.findById(userId);
        
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Unauthorized: User not found.", null), HttpStatus.UNAUTHORIZED);
        }

        try {
            String newName = request.get("name");
            String newAddress = request.get("address");
            String newPhoneNumber = request.get("phoneNumber");

            Profile updatedProfile = profileService.updateProfileByUserId(userId, newName, newAddress, newPhoneNumber);

            if (updatedProfile == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "Profile not found for the user.", null), HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(new ApiResponse<>(false, "Profile updated successfully.", updatedProfile), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>(true, "Failed to update account.", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
