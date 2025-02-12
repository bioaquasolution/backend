package com.thexbyte.bioaqua.controllers;

import com.thexbyte.bioaqua.services.AlertService;
import com.thexbyte.bioaqua.utils.AlertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
@Tag(name = "Alert Management", description = "APIs for managing alerts in BioAqua Solutions")
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    @Operation(summary = "Get all alerts", description = "Retrieves a list of all alerts.")
    public ResponseEntity<?> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @PostMapping
    @Operation(summary = "Create a new alert", description = "Saves a new alert in the system.")
    public ResponseEntity<?> createAlert(@RequestBody AlertRequest alert) {
        return alertService.save(alert);
    }

    @GetMapping("/system/{id}")
    @Operation(summary = "Get alerts by system ID", description = "Retrieves alerts related to a specific RO system.")
    public ResponseEntity<?> getAlertsBySystemId(@PathVariable("id") Long sysId) {
        return alertService.getAlertsByRoSystemId(sysId);
    }
}
