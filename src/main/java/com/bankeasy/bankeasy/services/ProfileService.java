package com.bankeasy.bankeasy.services;

import java.util.UUID;

import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

public interface ProfileService {
	
    Profile createProfile(User user, String name, String address, String phoneNumber);
    
    Profile updateProfileByUserId(UUID userId, String newName, String newAddress, String newPhoneNumber);
    
    Profile getProfileByUserId(UUID userId);
    
//    void deleteProfile(Profile profile);
	

 }

