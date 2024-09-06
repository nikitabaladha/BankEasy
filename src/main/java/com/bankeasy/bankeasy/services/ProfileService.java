 package com.bankeasy.bankeasy.services;

import java.time.LocalDate;  
import java.util.UUID;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

public interface ProfileService {
	
    Profile createProfile(User user, String firstName, String lastName,LocalDate dateOfBirth, String phoneNumber, String address,  String city,String state,String zipCode, 
                           String country, String maritalStatus, String occupation,   String accountType);
    
    Profile getProfileByUserId(UUID userId);
    
	Profile updateProfile(Profile existingProfile);
}
