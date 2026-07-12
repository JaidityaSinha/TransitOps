package com.transitops.backend.controller;

import com.transitops.backend.entity.Vehicle;
import com.transitops.backend.service.VehicleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER')")
    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER')")
    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id,
                                 @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
    }
}