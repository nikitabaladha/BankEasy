package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.Transfer;

import java.util.List;
import java.util.UUID;

public interface TransferDao extends JpaRepository<Transfer, UUID> {

    List<Transfer> findByUserId(UUID userId);

}
