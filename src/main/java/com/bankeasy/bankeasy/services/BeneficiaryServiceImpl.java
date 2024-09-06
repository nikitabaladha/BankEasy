package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.Profile;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.dao.BeneficiaryDao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryDao beneficiaryDao;

    @Override
    public Beneficiary create(User user, String name, String bankName, String accountNumber, String ifscCode) {

        Beneficiary beneficiary = new Beneficiary(user, name, bankName, accountNumber, ifscCode);
        return beneficiaryDao.save(beneficiary);
    }
    
    @Override
    public Beneficiary updateBeneficiaryById(Beneficiary beneficiary) {
        return beneficiaryDao.save(beneficiary);
    }

    @Override
    public Beneficiary getBeneficiaryById(UUID beneficiaryId) {
        return beneficiaryDao.findById(beneficiaryId).orElse(null); 
    }
    
    @Override
    public List<Beneficiary> getAllBeneficiariesByUserId(UUID userId) {
        return beneficiaryDao.findAllByUserId(userId);
    }
}
