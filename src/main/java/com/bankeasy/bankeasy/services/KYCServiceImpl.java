package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.KYC;
import com.bankeasy.bankeasy.entities.KYC.VerificationStatus;
import com.bankeasy.bankeasy.entities.User;
import com.bankeasy.bankeasy.dao.KYCDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class KYCServiceImpl implements KYCService {

    @Autowired
    private KYCDao kycDao;

    @Override
    public KYC createKYC(User user, String documentType, String documentNumber, String documentUrl) {
       
        KYC kyc = new KYC(user, documentType, documentNumber, documentUrl, VerificationStatus.Pending); 
        return kycDao.save(kyc);
    }

    @Override
    public KYC updateKYC(KYC kyc) {
        return kycDao.save(kyc);
    }
  
    @Override
    public KYC getKYCByUserId(UUID userId) {
        KYC kyc = kycDao.findByUserId(userId);

        return kyc;
    }

    @Override
    public List<KYC> getAllKYCsByUserId(UUID userId) {
        return kycDao.findAllByUserId(userId);
    }

	@Override
	public KYC findByUserId(UUID id) {
		return kycDao.findByUserId(id); 
	}
	
	@Override
    public KYC getApprovedKYCByUserId(UUID userId) {
        return kycDao.findByUserIdAndVerified(userId, VerificationStatus.Approved).orElse(null);
    }
	
	@Override
    public void updateKYCStatus(UUID userId, KYC.VerificationStatus status) {
        KYC kyc = kycDao.findByUserId(userId);
           
        kyc.setVerified(status);
        kycDao.save(kyc);
    }
	
	@Override
	public List<KYC> getPendingKYC() {
	    return kycDao.findByVerified(KYC.VerificationStatus.Pending);
	}
	
	public KYC getPendingKYCByUserId(UUID userId) {
		return kycDao.findByUserId(userId);
	}
		
}

