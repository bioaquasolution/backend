package com.thexbyte.bioaqua.services;

import com.thexbyte.bioaqua.entites.Alert;
import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.repositories.AlertRepository;
import com.thexbyte.bioaqua.repositories.RoSystemRepository;
import com.thexbyte.bioaqua.utils.ResponseMsg;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
 

    @Autowired
    private RoSystemRepository roSystemRepository;

    @Autowired
    private AlertRepository alertRepository;

     public Alert save(Alert Alert) {
        return alertRepository.save(Alert);
    }

     public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

     public Optional<Alert> getAlertById(Long id) {
        return alertRepository.findById(id);
    }

     public void deleteAlert(Long id) {
        alertRepository.deleteById(id);
    }

    public ResponseEntity<?> getAlertsByRoSystemId(Long id) {
        Optional<RoSystem> roSystem = roSystemRepository.findById(id);
        if (roSystem.isPresent()){
            return ResponseEntity.ok(alertRepository.findByRoSystemsAlerts(roSystem.get()));
        }
        else
            return  ResponseEntity.status(400).body("no system exist");

    }
}
