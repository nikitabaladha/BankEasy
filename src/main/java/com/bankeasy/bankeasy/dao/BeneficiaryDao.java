package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankeasy.bankeasy.entities.Beneficiary;

import java.util.List;
import java.util.UUID;

public interface BeneficiaryDao extends JpaRepository<Beneficiary, UUID> {
    Beneficiary findByUserId(UUID userId);
  
    List<Beneficiary> findAllByUserId(UUID userId);
}






