package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.User;

import java.util.List;
import java.util.UUID;

public interface KYCService {
    
    KYC createKYC(User user, String documentType, String documentUrl, String documentNumber);
    
    KYC updateKYC(KYC kyc);
    
    List<KYC> getAllKYCsByUserId(UUID userId);

	KYC getKYCByUserId(UUID userId);

	KYC findByUserId(UUID id);	
	
	KYC getApprovedKYCByUserId(UUID userId); 
	
	void updateKYCStatus(UUID userId, KYC.VerificationStatus status);
	
}

