package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.dao.AccountDao;
import com.bankeasy.bankeasy.dao.TransferDao;
import com.bankeasy.bankeasy.entities.Transfer;
import com.bankeasy.bankeasy.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferDao transferDao;
    

    @Override
    public Transfer createTransfer(User user, UUID beneficiaryId, BigDecimal amount, String remark) {
       
        Transfer transfer = new Transfer(user, beneficiaryId, amount, remark);
        return transferDao.save(transfer);
    }
    
  
    
	@Override
	public Transfer getTransferById(UUID transferId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transfer> getTransfersByUserId(UUID userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTransfer(UUID transferId) {
		// TODO Auto-generated method stub
		
	}
    
//    @Override
//    public Transfer getTransferById(UUID transferId) {
//        return transferDao.findById(transferId)
//                .orElseThrow(() -> new RuntimeException("Transfer not found with ID: " + transferId));
//    }
//
//    @Override
//    public List<Transfer> getTransfersByUserId(UUID userId) {
//        return transferDao.findByUserId(userId);
//    }
//
//    @Override
//    public void deleteTransfer(UUID transferId) {
//        Transfer transfer = transferDao.findById(transferId)
//                .orElseThrow(() -> new RuntimeException("Transfer not found with ID: " + transferId));
//        transferDao.delete(transfer);
//    }
}
