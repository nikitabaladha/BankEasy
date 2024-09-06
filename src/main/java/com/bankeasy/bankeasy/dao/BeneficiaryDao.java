package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.Beneficiary.BeneficiaryStatus;

import java.util.List;
import java.util.UUID;

public interface BeneficiaryDao extends JpaRepository<Beneficiary, UUID> {
	List<Beneficiary> findAllByUserId(UUID userId);

	List<Beneficiary> findAllByUserIdAndStatus(UUID userId, BeneficiaryStatus active);
    
}







