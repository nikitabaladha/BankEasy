package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileValidator {

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters.")
    private String name;

    @NotBlank(message = "Address is required.")
    @Size(min = 10, max = 100, message = "Address must be between 10 and 100 characters.")
    private String address;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits.")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
