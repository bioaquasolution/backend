package com.thexbyte.bioaqua.services;

import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.repositories.ComponentRepository;
import com.thexbyte.bioaqua.repositories.RoSystemRepository;
import com.thexbyte.bioaqua.utils.ResponseMsg;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private RoSystemRepository roSystemRepository;

    // Create or Update a Component
    public Component saveComponent(Component component) {
        return componentRepository.save(component);
    }

    // Get all Components
    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    // Get a Component by ID
    public Optional<Component> getComponentById(Long id) {
        return componentRepository.findById(id);
    }

    // Delete a Component by ID
    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }

    public ResponseEntity<?> getComponentsByRoSystemId(Long id) {
        Optional<RoSystem> roSystem = roSystemRepository.findById(id);
        if (roSystem.isPresent()){
            return ResponseEntity.ok(componentRepository.findByRoSystemsComponents(roSystem.get()));
        }
        else
            return  ResponseEntity.status(400).body("no system exist");

    }
}
