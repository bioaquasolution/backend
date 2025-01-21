package com.thexbyte.bioaqua.services;
 
import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.repositories.ComponentRepository;
import com.thexbyte.bioaqua.repositories.RoSystemRepository;
import com.thexbyte.bioaqua.repositories.UserRepository;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoSystemService {

    @Autowired
    private RoSystemRepository roSystemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComponentRepository componentRepository;

    // Create or Update an RoSystem
    public RoSystem saveRoSystem(RoSystem roSystem) {
        return roSystemRepository.save(roSystem);
    }

    // Get all RoSystems
    public List<RoSystem> getAllRoSystems() {
        return roSystemRepository.findAll();
    }

    // Get an RoSystem by ID
    public Optional<RoSystem> getRoSystemById(Long id) {
        return roSystemRepository.findById(id);
    }

    // Delete an RoSystem
    public void deleteRoSystem(Long id) {
        roSystemRepository.deleteById(id);
    }

    // Add a Component to an RoSystem
    public RoSystem addComponentToRoSystem(Long roSystemId, Long componentId) {
        RoSystem roSystem = roSystemRepository.findById(roSystemId)
                .orElseThrow(() -> new RuntimeException("RoSystem not found"));
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found"));

        roSystem.getComponents().add(component);
        return roSystemRepository.save(roSystem);
    }

    // Remove a Component from an RoSystem
    public RoSystem removeComponentFromRoSystem(Long roSystemId, Long componentId) {
        RoSystem roSystem = roSystemRepository.findById(roSystemId)
                .orElseThrow(() -> new RuntimeException("RoSystem not found"));
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found"));

        roSystem.getComponents().remove(component);
        return roSystemRepository.save(roSystem);
    }
}
