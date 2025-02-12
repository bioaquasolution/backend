package com.thexbyte.bioaqua.controllers;

import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.services.ComponentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@Tag(name = "Components", description = "Endpoints for managing components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @Operation(summary = "Get all components", description = "Retrieve a list of all components")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved components"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<Component>> getAllComponents() {
        return ResponseEntity.ok(componentService.getAllComponents());
    }

    @Operation(summary = "Get component by ID", description = "Retrieve a component by its unique ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved component"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        return componentService.getComponentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create or update a component", description = "Create a new component or update an existing one")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Component created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid component data")
    })
    @PostMapping
    public ResponseEntity<Component> createOrUpdateComponent(@RequestBody Component component) {
        return ResponseEntity.ok(componentService.saveComponent(component));
    }

    @Operation(summary = "Delete component by ID", description = "Delete a component by its unique ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Component successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get components by RO system ID", description = "Retrieve all components associated with a specific RO system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved components for the system"),
        @ApiResponse(responseCode = "404", description = "No components found for the given system ID")
    })
    @GetMapping("/system/{id}")
    public ResponseEntity<?> getComponentsByRoSystemId(@PathVariable Long id) {
        return componentService.getComponentsByRoSystemId(id);
    }
}
