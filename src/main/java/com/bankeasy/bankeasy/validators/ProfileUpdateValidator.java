package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class ProfileUpdateValidator {

    @Size(min = 2, max = 30, message = "Firstname must be between 2 and 30 characters.")
    private String firstName;

    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters.")
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits.")
    private String phoneNumber;

    @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters.")
    private String address;

    @Size(max = 100, message = "City name must not exceed 100 characters.")
    private String city;

    @Size(max = 100, message = "State name must not exceed 100 characters.")
    private String state;

    @Size(max = 20, message = "Zip code must not exceed 20 characters.")
    private String zipCode;

    @Size(max = 100, message = "Country name must not exceed 100 characters.")
    private String country;

    @Size(max = 20, message = "Marital status must not exceed 20 characters.")
    private String maritalStatus;

    @Size(max = 100, message = "Occupation must not exceed 100 characters.")
    private String occupation;

    private LocalDate dateOfBirth;

//    private String accountType;

    // Getters and setters for all fields

//    public String getAccountType() {
//        return accountType;
//    }
//
//    public void setAccountType(String accountType) {
//        this.accountType = accountType;
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
