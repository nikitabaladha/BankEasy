package com.bankeasy.bankeasy.controller;

import com.bankeasy.bankeasy.entities.Account;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.reqres.ApiResponse;
import com.bankeasy.bankeasy.services.AccountService;
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

    @Autowired
    private AccountService accountService;

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

            Account account = accountService.findByUserId(user.getId());
            if (account == null) {
                return new ResponseEntity<>(new ApiResponse<>(true, "No account found for this user.", null), HttpStatus.BAD_REQUEST);
            }

            Profile profile = profileService.createProfile(user, account, name, address, phoneNumber);
            return new ResponseEntity<>(new ApiResponse<>(false, "Profile created successfully.", profile), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Invalid input: " + e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(true, "An error occurred: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
