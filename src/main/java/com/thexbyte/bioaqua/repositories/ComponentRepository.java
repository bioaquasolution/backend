package com.thexbyte.bioaqua.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.entites.RoSystem;

public interface ComponentRepository extends JpaRepository<Component , Long>  {

     List<Component> findByRoSystemsComponents(RoSystem roSystem);
    
}
