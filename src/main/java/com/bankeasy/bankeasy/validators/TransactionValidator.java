package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


import com.bankeasy.bankeasy.entities.Transaction;

public class TransactionValidator {

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero.")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required.")
    private Transaction.TransactionType transactionType;

    @Size(max = 500, message = "Description must be less than 500 characters.")
    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
