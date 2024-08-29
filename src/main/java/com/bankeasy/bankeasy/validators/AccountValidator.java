package com.bankeasy.bankeasy.validators;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;


public class AccountValidator {
		@DecimalMin(value = "0.0", inclusive = true, message = "Balance must be greater than or equal to zero.")
	    private BigDecimal balance = BigDecimal.ZERO;
	    

	    public BigDecimal getBalance() {
	        return balance;
	    }

	    public void setBalance(BigDecimal balance) {
	        this.balance = balance;
	    }
	    
}

