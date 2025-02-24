package com.thexbyte.bioaqua.repositories;

import com.thexbyte.bioaqua.entites.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByClientId(Long clientId);
    List<Billing> findByRoSystemId(Long systemId);
} 