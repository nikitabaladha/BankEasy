package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.ProfileDao;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ProfileServiceImpl implements ProfileService {

	@Autowired
    private ProfileDao profileDao;

    @Override
    public Profile createProfile(User user, String firstName,String lastName, String address, String phoneNumber, String city, String state, String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth, String accountType) {
        
        Profile profile = new Profile(user, firstName, lastName, address, phoneNumber, city, state, zipCode, country, maritalStatus, occupation, dateOfBirth,accountType);
        return profileDao.save(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
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

    // @Override
    // public void deleteProfile(Profile profile) {
    // profile.setProfileStatus("Deleted");
    // profileDao.save(profile);
    //
    // }
}

