package com.thexbyte.bioaqua.repositories;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.entites.User;

import java.util.List;
import java.util.Optional;

public interface RoSystemRepository extends JpaRepository<RoSystem , Long> {
    List<RoSystem> findAllByOwner(User user);
}