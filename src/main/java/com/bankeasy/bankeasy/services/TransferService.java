package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Transfer;
import com.bankeasy.bankeasy.entities.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransferService {

    Transfer createTransfer(User user, UUID beneficiaryId, BigDecimal amount, String remark);

    Transfer getTransferById(UUID transferId);

    List<Transfer> getTransfersByUserId(UUID userId);

    void deleteTransfer(UUID transferId);
}
