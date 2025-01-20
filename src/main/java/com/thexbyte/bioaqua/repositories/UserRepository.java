package com.thexbyte.bioaqua.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thexbyte.bioaqua.entites.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {
    Optional<User> findByEmail(String email);
}