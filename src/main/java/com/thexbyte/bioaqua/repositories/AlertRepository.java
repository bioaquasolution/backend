package com.thexbyte.bioaqua.repositories;

import com.thexbyte.bioaqua.entites.Alert;
import com.thexbyte.bioaqua.entites.RoSystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long>  {
    List<Alert> findAllByRoSystem(RoSystem system);
}