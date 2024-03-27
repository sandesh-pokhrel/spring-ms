package com.sandesh.libraryservice.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

@Component(value = "auditAwareRef")
public class EntityAuditAware implements AuditorAware<Integer> {

    private final Random random = new Random();

    @Override
    @NonNull
    public Optional<Integer> getCurrentAuditor() {
        return Optional.of(random.nextInt(100, 999));
    }
}
