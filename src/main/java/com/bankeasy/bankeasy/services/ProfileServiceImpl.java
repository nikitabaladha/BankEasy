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
    public Profile createProfile(User user, String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber,String address,
            String city, String state, String zipCode, String country, String maritalStatus, String occupation, String accountType) {
        
        Profile profile = new Profile(user, firstName, lastName, dateOfBirth,  phoneNumber, address,
                city, state, zipCode,  country,  maritalStatus, occupation, accountType);
        
        return profileDao.save(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileDao.save(profile);
    }

    @Override
    public Profile getProfileByUserId(UUID userId) {

        Profile profile = profileDao.findByUserId(userId);

        return profile;
    }

  
}

