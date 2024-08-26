package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Beneficiary;
import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.dao.KYCDao;  
import com.bankeasy.bankeasy.services.KYCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KYCServiceImpl implements KYCService {

    @Autowired
    private KYCDao kycDao; 

    @Override
    public KYC createKYC(User user, String documentType, String documentNumber) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        KYC kyc = new KYC(user, documentType, documentNumber, Boolean.FALSE); 
        return kycDao.save(kyc);
    }

    @Override
    public KYC updateKYCByUserId(UUID userId, String documentType, String documentNumber) {
        KYC kyc = kycDao.findByUserId(userId);
        if (kyc == null) {
            throw new IllegalArgumentException("KYC not found for userId: " + userId);
        }

        kyc.setDocumentType(documentType);
        kyc.setDocumentNumber(documentNumber);
      
        return kycDao.save(kyc);
    }

    @Override
    public KYC getKYCByUserId(UUID userId) {
        return kycDao.findByUserId(userId); 
    }

    @Override
    public List<KYC> getAllKYCsByUserId(UUID userId) {
        return kycDao.findAllByUserId(userId);
    }
    
    
}
