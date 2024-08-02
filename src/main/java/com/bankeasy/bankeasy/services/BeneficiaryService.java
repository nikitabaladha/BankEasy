package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.User;
import java.util.UUID;

public interface BeneficiaryService {
    Beneficiary create(User user, String name, String bankName, String accountNumber, String ifscCode);
    Beneficiary updateBeneficiaryByUserId(UUID userId, String name, String bankName, String accountNumber, String ifscCode);
    Beneficiary getBeneficiaryByUserId(UUID userId);
    Beneficiary findByUserId(UUID userId);
}
