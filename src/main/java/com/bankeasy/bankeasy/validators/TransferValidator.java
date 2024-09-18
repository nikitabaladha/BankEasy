package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferValidator {

    @NotNull(message = "Beneficiary ID cannot be null.")
    private UUID beneficiaryId;

    @NotNull(message = "Amount cannot be null.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Amount must be greater than or equal to zero.")
    private BigDecimal amount;

    @NotBlank(message = "Remark cannot be blank.")
    private String remark;

    public UUID getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(UUID beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
