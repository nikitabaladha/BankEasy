package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Beneficiary;
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
//        if (user == null) {
//            throw new IllegalArgumentException("User cannot be null");
//        }

        Beneficiary beneficiary = new Beneficiary(user, name, bankName, accountNumber, ifscCode);
        return beneficiaryDao.save(beneficiary);
    }

    @Override
    public Beneficiary updateBeneficiaryByUserId(UUID userId, String name, String bankName, String accountNumber, String ifscCode) {
        Beneficiary beneficiary = beneficiaryDao.findByUserId(userId);
//        if (beneficiary == null) {
//            throw new IllegalArgumentException("Beneficiary not found for userId: " + userId);
//        }
     
        beneficiary.setName(name);
        beneficiary.setBankName(bankName);
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setIfscCode(ifscCode);
       
        return beneficiaryDao.save(beneficiary);
    }


    @Override
    public Beneficiary getBeneficiaryByUserId(UUID userId) {
        return beneficiaryDao.findByUserId(userId);
    }

    @Override
    public Beneficiary findByUserId(UUID userId) {
        return beneficiaryDao.findByUserId(userId);
    }
    
    @Override
    public List<Beneficiary> getAllBeneficiariesByUserId(UUID userId) {
        return beneficiaryDao.findAllByUserId(userId);
    }
    

}
