package com.bankeasy.bankeasy.dao;

import com.bankeasy.bankeasy.entities.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface KYCDao extends JpaRepository<KYC, UUID> {
    
    KYC findByUserId(UUID userId);

    List<KYC> findAllByUserId(UUID userId);
}

