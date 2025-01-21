package com.thexbyte.bioaqua.controllers;

import com.thexbyte.bioaqua.entites.Component;
import com.thexbyte.bioaqua.services.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // Get all Components
    @GetMapping
    public ResponseEntity<List<Component>> getAllComponents() {
        return ResponseEntity.ok(componentService.getAllComponents());
    }

    // Get a Component by ID
    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        return componentService.getComponentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create or Update a Component
    @PostMapping
    public ResponseEntity<Component> createOrUpdateComponent(@RequestBody Component component) {
        return ResponseEntity.ok(componentService.saveComponent(component));
    }

    // Delete a Component by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/system/{id}")
    public ResponseEntity<?> getComponentsByRoSystemId(@PathVariable Long id) {
        return  componentService.getComponentsByRoSystemId(id);
    }   
}
