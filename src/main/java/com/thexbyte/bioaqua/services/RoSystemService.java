package com.thexbyte.bioaqua.services;
 
import aj.org.objectweb.asm.commons.Remapper;
import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.entites.User;
import com.thexbyte.bioaqua.repositories.ComponentRepository;
import com.thexbyte.bioaqua.repositories.RoSystemRepository;
import com.thexbyte.bioaqua.repositories.UserRepository;

import com.thexbyte.bioaqua.utils.ResponseMsg;
import com.thexbyte.bioaqua.utils.RoSystemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> saveRoSystem(RoSystemRequest roSystem) {
        Optional<User> owner = userRepository.findById(roSystem.getOwnerId());
        if (owner.isPresent()){
            RoSystem system = new RoSystem();
            system.setCapacity(roSystem.getCapacity());
            system.setName(roSystem.getName());
            system.setDimensions(roSystem.getDimensions());
            system.setSerialNumber(roSystem.getSerialNumber());
            system.setInstallationDate(roSystem.getInstallationDate());
            system.setModel(roSystem.getModel());
            system.setOwner(owner.get());
            return ResponseEntity.ok( roSystemRepository.save(system));

        }else
            return ResponseEntity.status(400).body(new ResponseMsg("mal formatted data"));
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

    public ResponseEntity<?> getRoSystemByOwnerId(Long id) {
        Optional<User> owner = userRepository.findById(id);
        if (owner.isPresent()){
            return ResponseEntity.ok(roSystemRepository.findAllByOwner(owner.get()));
        }
        return ResponseEntity.status(400).body(new ResponseMsg("no systems found"));
    }
}
