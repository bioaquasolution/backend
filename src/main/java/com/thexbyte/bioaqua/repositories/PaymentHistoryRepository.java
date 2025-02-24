package com.thexbyte.bioaqua.repositories;

import com.thexbyte.bioaqua.entites.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
} 