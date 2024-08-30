package com.bankeasy.bankeasy.reqres;

import jakarta.validation.constraints.NotBlank;

public class AccountCreateRequest {
	
@NotBlank(message = "Account type is required")
  private String accountType;

   public String getAccountType() {
        return accountType;
    }

  public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
