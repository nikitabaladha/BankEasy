package com.bankeasy.bankeasy.services;

import com.bankeasy.bankeasy.entities.Transfer;
import com.bankeasy.bankeasy.entities.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferService {

    Transfer createTransfer(User user, UUID beneficiaryId, BigDecimal amount, String remark);
}
