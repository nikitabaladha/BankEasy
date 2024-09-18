package com.bankeasy.bankeasy.validators;

import com.bankeasy.bankeasy.entities.Beneficiary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BeneficiaryValidator {

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters.")
    private String name;
    
    @NotBlank(message = "Bank name is required.")
    @Size(min = 2, max = 30, message = "Bank Name must be between 2 and 30 characters.")
    private String bankName;
    
    @NotBlank(message = "IFSC code is required.")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "IFSC Code must be in the format XXXX0YYYYYY (India).")
    @Pattern(regexp = "^[A-Z0-9]{6,20}$", message = "IFSC Code must be between 6 and 20 characters (Global).")
    private String ifscCode;
    
    @NotBlank(message = "Account Number is required.")
    @Size(min = 10, max = 20, message = "Account Number must be between 10 and 20 characters.")
    private String accountNumber;
    
    private Beneficiary.BeneficiaryStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Beneficiary.BeneficiaryStatus getStatus() {
        return status;
    }

    public void setStatus(Beneficiary.BeneficiaryStatus status) {
        this.status = status;
    }
}
