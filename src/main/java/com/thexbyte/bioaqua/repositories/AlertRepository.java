package com.thexbyte.bioaqua.repositories;

import com.thexbyte.bioaqua.entites.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long>  {
       
}