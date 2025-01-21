package com.thexbyte.bioaqua.controllers;

 import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.services.RoSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/systems")
public class RoSystemController {

    @Autowired
    private RoSystemService roSystemService;

    // Get all RoSystems
    @GetMapping
    public ResponseEntity<List<RoSystem>> getAllRoSystems() {
        return ResponseEntity.ok(roSystemService.getAllRoSystems());
    }

    // Get an RoSystem by ID
    @GetMapping("/{id}")
    public ResponseEntity<RoSystem> getRoSystemById(@PathVariable Long id) {
        return roSystemService.getRoSystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RoSystem> createOrUpdateRoSystem(@RequestBody RoSystem roSystem) {
        return ResponseEntity.ok(roSystemService.saveRoSystem(roSystem));
    }

    // Delete an RoSystem by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoSystem(@PathVariable Long id) {
        roSystemService.deleteRoSystem(id);
        return ResponseEntity.noContent().build();
    }

    // Add a Component to an RoSystem
    @PostMapping("/{roSystemId}/components/{componentId}")
    public ResponseEntity<RoSystem> addComponentToRoSystem(
            @PathVariable Long roSystemId,
            @PathVariable Long componentId
    ) {
        return ResponseEntity.ok(roSystemService.addComponentToRoSystem(roSystemId, componentId));
    }

    // Remove a Component from an RoSystem
    @DeleteMapping("/{roSystemId}/components/{componentId}")
    public ResponseEntity<RoSystem> removeComponentFromRoSystem(
            @PathVariable Long roSystemId,
            @PathVariable Long componentId
    ) {
        return ResponseEntity.ok(roSystemService.removeComponentFromRoSystem(roSystemId, componentId));
    }
}
