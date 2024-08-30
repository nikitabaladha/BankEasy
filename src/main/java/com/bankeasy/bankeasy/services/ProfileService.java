 package com.bankeasy.bankeasy.services;

import java.time.LocalDate;  
import java.util.UUID;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

public interface ProfileService {
	
    Profile createProfile(User user, String firstName, String lastName, String address, String phoneNumber, String city, String state,
                          String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth, String accountType);
    
    Profile getProfileByUserId(UUID userId);
    

	Profile updateProfile(Profile existingProfile);

	
     
    // void deleteProfile(Profile profile);
}
