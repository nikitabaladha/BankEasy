package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.ProfileDao;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileDao profileDao;
    
    public Profile createProfile(User user, String name, String address, String phoneNumber) {
        Profile profile = new Profile(user, name, address, phoneNumber);
        return profileDao.save(profile);
    }

    @Override
    public Profile updateProfileByUserId(UUID userId, String newName, String newAddress, String newPhoneNumber) {
        Profile profile = profileDao.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }
        profile.setName(newName);
        profile.setAddress(newAddress);
        profile.setPhoneNumber(newPhoneNumber);
        return profileDao.save(profile);
    }

    @Override
    public Profile getProfileByUserId(UUID userId) {
        Profile profile = profileDao.findByUserId(userId);
        if (profile == null) {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }
        return profile;
    }


//	@Override
//	public void deleteProfile(Profile profile) {
//		 profile.setProfileStatus("Deleted");
//	        profileDao.save(profile);
//		
//	}
}


