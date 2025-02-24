package com.thexbyte.bioaqua.controllers;

import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.services.RoSystemService;
import com.thexbyte.bioaqua.utils.RoSystemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/systems")
@Tag(name = "RO Systems", description = "APIs for managing RO water purification systems") // API Tag
public class RoSystemController {

    @Autowired
    private RoSystemService roSystemService;

    @GetMapping
    @Operation(summary = "Get all RO systems", description = "Retrieve a list of all registered RO systems")
    public ResponseEntity<List<RoSystem>> getAllRoSystems() {
        return ResponseEntity.ok(roSystemService.getAllRoSystems());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an RO system by ID", description = "Retrieve an RO system by its unique ID")
    public ResponseEntity<RoSystem> getRoSystemById(@PathVariable Long id) {
        return roSystemService.getRoSystemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{id}")
    @Operation(summary = "Get RO systems by owner ID", description = "Retrieve all RO systems belonging to a specific owner")
    public ResponseEntity<?> getRoSystemByOwnerId(@PathVariable Long id) {
        return roSystemService.getRoSystemByOwnerId(id);
    }

    @PostMapping
    @Operation(summary = "Create or update an RO system", description = "Create a new RO system or update an existing one")
    public ResponseEntity<?> createOrUpdateRoSystem(@RequestBody RoSystemRequest roSystem) {
        return roSystemService.saveRoSystem(roSystem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an RO system", description = "Remove an RO system from the system by its ID")
    public ResponseEntity<Void> deleteRoSystem(@PathVariable Long id) {
        roSystemService.deleteRoSystem(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roSystemId}/components/{componentId}")
    @Operation(summary = "Add a component to an RO system", description = "Associate a component with a specific RO system")
    public ResponseEntity<RoSystem> addComponentToRoSystem(
            @PathVariable Long roSystemId,
            @PathVariable Long componentId
    ) {
        return ResponseEntity.ok(roSystemService.addComponentToRoSystem(roSystemId, componentId));
    }

    @DeleteMapping("/{roSystemId}/components/{componentId}")
    @Operation(summary = "Remove a component from an RO system", description = "Dissociate a component from a specific RO system")
    public ResponseEntity<RoSystem> removeComponentFromRoSystem(
            @PathVariable Long roSystemId,
            @PathVariable Long componentId
    ) {
        return ResponseEntity.ok(roSystemService.removeComponentFromRoSystem(roSystemId, componentId));
    }
}
