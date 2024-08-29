 package com.bankeasy.bankeasy.services;

import java.time.LocalDate;  
import java.util.UUID;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

public interface ProfileService {
	
    Profile createProfile(User user, String firstName, String lastName, String address, String phoneNumber, String city, String state,
                          String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth);
    
    Profile getProfileByUserId(UUID userId);
    
   
     Profile updateProfileByUserId(UUID userId, String newFirstName,String newLastName, String newAddress, String newPhoneNumber,String city, String state,
             String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth);
     
    // void deleteProfile(Profile profile);
}
