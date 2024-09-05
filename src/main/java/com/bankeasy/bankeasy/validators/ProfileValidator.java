package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate; 

public class ProfileValidator {

	@NotBlank(message = "Firstname is required.")
    @Size(min = 2, max = 30, message = "Firstname must be between 2 and 30 characters.")
    private String firstName;
    
    @NotBlank(message = "Lastname is required.")
    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30 characters.")
    private String lastName;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits.")
    private String phoneNumber;

    @NotBlank(message = "Address is required.")
    @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters.")
    private String address;

    @NotBlank(message = "City is required.")
    @Size(max = 100, message = "City name must not exceed 100 characters.")
    private String city;

    @NotBlank(message = "State is required.")
    @Size(max = 100, message = "State name must not exceed 100 characters.")
    private String state;

    @NotBlank(message = "Zip code is required.")
    @Size(max = 20, message = "Zip code must not exceed 20 characters.")
    private String zipCode;

    @NotBlank(message = "Country is required.")
    @Size(max = 100, message = "Country name must not exceed 100 characters.")
    private String country;

    @NotBlank(message = "Marital status is required.")
    @Size(max = 20, message = "Marital status must not exceed 20 characters.")
    private String maritalStatus;

    @NotBlank(message = "Occupation is required.")
    @Size(max = 100, message = "Occupation must not exceed 100 characters.")
    private String occupation;

    @NotNull(message = "Date of birth is required.") 
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "Account type is required")
    private String accountType;


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
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    
    public String getAccountType() {
        return accountType;
    }

  public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
