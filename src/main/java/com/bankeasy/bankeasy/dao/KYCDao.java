package com.bankeasy.bankeasy.dao;

import com.bankeasy.bankeasy.entities.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KYCDao extends JpaRepository<KYC, UUID> {
    
    KYC findByUserId(UUID userId);

    List<KYC> findAllByUserId(UUID userId);
    
    Optional<KYC> findByUserIdAndVerified(UUID userId, KYC.VerificationStatus verifiedStatus);
    
    List<KYC> findByVerified(KYC.VerificationStatus status);
    
    KYC findAllByUserIdAndVerified(UUID userId, KYC.VerificationStatus verifiedStatus);
    
}

