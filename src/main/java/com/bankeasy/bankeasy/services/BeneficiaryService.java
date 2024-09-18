package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.User;

import java.util.List;
import java.util.UUID;

public interface BeneficiaryService {
    Beneficiary create(User user, String name, String bankName, String accountNumber, String ifscCode);
    
    Beneficiary updateBeneficiaryById(Beneficiary existingBeneficiary);
    
    Beneficiary getBeneficiaryById(UUID beneficiaryId);
    
    List<Beneficiary> getAllBeneficiariesByUserId(UUID userId);
    
    List<Beneficiary> getActiveBeneficiariesByUserId(UUID userId);
    
    Beneficiary softDeleteBeneficiaryById(UUID beneficiaryId);
       
}
