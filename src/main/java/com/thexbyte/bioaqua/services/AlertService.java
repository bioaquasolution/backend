package com.thexbyte.bioaqua.services;

import com.thexbyte.bioaqua.entites.Alert;
import com.thexbyte.bioaqua.entites.RoSystem;
import com.thexbyte.bioaqua.repositories.AlertRepository;
import com.thexbyte.bioaqua.repositories.RoSystemRepository;
import com.thexbyte.bioaqua.utils.AlertRequest;
import com.thexbyte.bioaqua.utils.ResponseMsg;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {
 

    @Autowired
    private RoSystemRepository roSystemRepository;

    @Autowired
    private AlertRepository alertRepository;

     public ResponseEntity<?> save(AlertRequest alertRequest) {
        Optional<RoSystem> system = roSystemRepository.findById(alertRequest.getSystemId());
        if (system.isPresent()) {
            Alert alert = new Alert();
            alert.setTitle(alertRequest.getTitle());
            alert.setAlertDate(alertRequest.getAlertDate());
            alert.setContent(alertRequest.getContent());
            alert.setSeverity(alertRequest.getSiverity());
            alert.setRoSystem(system.get());
            return ResponseEntity.ok(  alertRepository.save(alert));
        }else 
        return ResponseEntity.status(400).body(new ResponseMsg("no system found"));
    }

     public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok( alertRepository.findAll());
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
            return ResponseEntity.ok(alertRepository.findAllByRoSystem(roSystem.get()));
        }
        else
            return  ResponseEntity.status(400).body(new ResponseMsg( "no system exist"));

    }
}
