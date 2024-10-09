package org.example.expert.domain.common.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.config.CustomHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {
    private final CustomHealthIndicator healthIndicator;

    @GetMapping("/health")
    public ResponseEntity<Health> checkHealth() {
        Health health = healthIndicator.health();

        if (health.getStatus().equals(Status.UP)) {
            return ResponseEntity.ok(health);
        } else {
            return ResponseEntity.status(503).body(health);
        }
    }
}
