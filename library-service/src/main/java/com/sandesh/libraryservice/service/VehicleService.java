package com.sandesh.libraryservice.service;

import com.sandesh.libraryservice.model.Owner;
import com.sandesh.libraryservice.model.Vehicle;
import com.sandesh.libraryservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public void removeOneOwner(long vehicleId, long ownerId) {
        log.info("Fetching vehicle..");
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        log.info("Fetching owners lazily..");
        vehicleOpt.ifPresent(vehicle -> {
            vehicle.getOwners().stream().filter(owner -> owner.getId() == ownerId).findFirst()
                            .ifPresent(owner -> vehicle.getOwners().remove(owner));

        });
    }
}
