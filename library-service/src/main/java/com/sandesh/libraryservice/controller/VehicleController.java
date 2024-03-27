package com.sandesh.libraryservice.controller;

import com.sandesh.libraryservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/{vehicleId}/{ownerId}")
    public void removeOneOwnerByVehicle(@PathVariable Long vehicleId, @PathVariable Long ownerId) {
        vehicleService.removeOneOwner(vehicleId, ownerId);
    }
}
