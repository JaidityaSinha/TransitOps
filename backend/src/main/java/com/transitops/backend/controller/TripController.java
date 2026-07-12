package com.transitops.backend.controller;

import com.transitops.backend.entity.Trip;
import com.transitops.backend.service.TripService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','DISPATCHER','FINANCIAL_ANALYST')")
    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','DISPATCHER','FINANCIAL_ANALYST')")
    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','DISPATCHER')")
    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.saveTrip(trip);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FLEET_MANAGER','DISPATCHER')")
    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id,
                           @RequestBody Trip trip) {
        return tripService.updateTrip(id, trip);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}