package com.thexbyte.bioaqua.repositories;

import com.thexbyte.bioaqua.entites.RecurringBilling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecurringBillingRepository extends JpaRepository<RecurringBilling, Long> {
    List<RecurringBilling> findByClientId(Long clientId);
} 