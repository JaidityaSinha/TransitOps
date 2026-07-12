package com.transitops.backend.controller;

import com.transitops.backend.entity.Driver;
import com.transitops.backend.service.DriverService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','FINANCIAL_ANALYST')")
    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return driverService.getDriverById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER')")
    @PostMapping
    public Driver createDriver(@RequestBody Driver driver) {
        return driverService.saveDriver(driver);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER')")
    @PutMapping("/{id}")
    public Driver updateDriver(@PathVariable Long id,
                               @RequestBody Driver driver) {
        return driverService.updateDriver(id, driver);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
    }
}