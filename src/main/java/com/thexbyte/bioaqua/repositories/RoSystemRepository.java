package com.thexbyte.bioaqua.repositories;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.entites.User;

import java.util.Optional;

public interface RoSystemRepository extends JpaRepository<RoSystem , Long> {
    Optional<RoSystem> findAllByOwner(User user);
}