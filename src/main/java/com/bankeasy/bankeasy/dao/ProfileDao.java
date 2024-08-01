package com.bankeasy.bankeasy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bankeasy.bankeasy.entities.Profile;
import java.util.UUID;

public interface ProfileDao extends JpaRepository<Profile, UUID> {
    Profile findByUserId(UUID userId);
}
