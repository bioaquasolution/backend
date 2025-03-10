package com.thexbyte.bioaqua.controllers;

 import com.thexbyte.bioaqua.services.NotificationService;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestParam String deviceToken) {
        notificationService.sendPushNotification(deviceToken, "High TDS Alert", "Your TDS level is above 500 ppm.");
        return ResponseEntity.ok().build();
    }
}