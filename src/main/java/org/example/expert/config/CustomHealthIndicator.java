package org.example.expert.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Component
public class CustomHealthIndicator implements HealthIndicator {
    private static final long DISK_THRESHOLD = 10_000_000_000L; // 10 GB
    private static final double MEMORY_THRESHOLD = 0.9; // 90%


    private EntityManager entityManager;

    @Override
    public Health health() {
        boolean isMemoryHealthy = checkMemoryHealth();
        boolean isDiskHealthy = checkDiskHealth();

        if (isMemoryHealthy && isDiskHealthy) {
            return Health.up()
                    .withDetail("memory", "OK")
                    .withDetail("disk", "OK")
                    .withDetail("database", "OK")
                    .build();
        } else {
            return Health.down()
                    .withDetail("memory", isMemoryHealthy ? "OK" : "Low")
                    .withDetail("disk", isDiskHealthy ? "OK" : "Low")
                    .build();
        }
    }

    private boolean checkMemoryHealth() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return (double) usedMemory / maxMemory < MEMORY_THRESHOLD;
    }

    private boolean checkDiskHealth() {
        File root = new File("/");
        long freeSpace = root.getFreeSpace();
        return freeSpace > DISK_THRESHOLD;
    }

}