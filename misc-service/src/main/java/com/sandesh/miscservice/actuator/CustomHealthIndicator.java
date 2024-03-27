package com.sandesh.miscservice.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (5+2 == 7)
            return Health.up().build();
        else
            return Health.down().build();
    }
}
