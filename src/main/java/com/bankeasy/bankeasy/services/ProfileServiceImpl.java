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
    public Profile createProfile(User user, String firstName,String lastName, String address, String phoneNumber, String city, String state, String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth) {
        
        Profile profile = new Profile(user, firstName, lastName, address, phoneNumber, city, state, zipCode, country, maritalStatus, occupation, dateOfBirth);
        return profileDao.save(profile);
    }

    @Override
    public Profile updateProfileByUserId(UUID userId, String newFirstName, String newLastName, String newAddress, String newPhoneNumber,String city, String state, String zipCode, String country, String maritalStatus, String occupation, LocalDate dateOfBirth) {

        Profile profile = profileDao.findByUserId(userId);

        if (profile == null) {
            throw new RuntimeException("Profile not found for user ID: " + userId);
        }

        profile.setFirstName(newFirstName);
        profile.setLastName(newLastName);
        profile.setAddress(newAddress);
        profile.setPhoneNumber(newPhoneNumber);
        profile.setCity(city);
        profile.setState(state);
        profile.setZipCode(zipCode);
        profile.setCountry(country);
        profile.setMaritalStatus(maritalStatus);
        profile.setOccupation(occupation);
        profile.setDateOfBirth(dateOfBirth);

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

