package com.transitops.backend.service;

import com.transitops.backend.entity.Driver;
import com.transitops.backend.entity.Trip;
import com.transitops.backend.entity.Vehicle;
import com.transitops.backend.enums.DriverStatus;
import com.transitops.backend.enums.TripStatus;
import com.transitops.backend.enums.VehicleStatus;
import com.transitops.backend.exception.ResourceNotFoundException;
import com.transitops.backend.repository.DriverRepository;
import com.transitops.backend.repository.TripRepository;
import com.transitops.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public TripService(TripRepository tripRepository,
                       VehicleRepository vehicleRepository,
                       DriverRepository driverRepository) {
        this.tripRepository = tripRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with id " + id));
    }

    public Trip saveTrip(Trip trip) {

        Vehicle vehicle = vehicleRepository.findById(trip.getVehicle().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found"));

        Driver driver = driverRepository.findById(trip.getDriver().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        // Registration rules

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new IllegalArgumentException("Vehicle is not available.");
        }

        if (driver.getStatus() != DriverStatus.AVAILABLE) {
            throw new IllegalArgumentException("Driver is not available.");
        }

        if (driver.getLicenseExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Driver license has expired.");
        }

        if (trip.getCargoWeight() > vehicle.getMaxLoadCapacity()) {
            throw new IllegalArgumentException("Cargo exceeds vehicle capacity.");
        }

        // Auto-dispatch

        if (trip.getStatus() == TripStatus.DISPATCHED) {

            vehicle.setStatus(VehicleStatus.ON_TRIP);
            driver.setStatus(DriverStatus.ON_TRIP);

            vehicleRepository.save(vehicle);
            driverRepository.save(driver);
        }

        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long id, Trip updatedTrip) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with id " + id));

        TripStatus previousStatus = trip.getStatus();

        trip.setTripNumber(updatedTrip.getTripNumber());
        trip.setSource(updatedTrip.getSource());
        trip.setDestination(updatedTrip.getDestination());
        trip.setCargoWeight(updatedTrip.getCargoWeight());
        trip.setPlannedDistance(updatedTrip.getPlannedDistance());

        // NEW FIELDS

        trip.setActualDistance(updatedTrip.getActualDistance());
        trip.setFuelConsumed(updatedTrip.getFuelConsumed());
        trip.setRevenue(updatedTrip.getRevenue());
        trip.setDispatchDate(updatedTrip.getDispatchDate());
        trip.setCompletionDate(updatedTrip.getCompletionDate());

        trip.setStatus(updatedTrip.getStatus());

        Vehicle vehicle = trip.getVehicle();
        Driver driver = trip.getDriver();

        // Complete trip

        if (previousStatus == TripStatus.DISPATCHED &&
                updatedTrip.getStatus() == TripStatus.COMPLETED) {

            vehicle.setStatus(VehicleStatus.AVAILABLE);
            driver.setStatus(DriverStatus.AVAILABLE);

            vehicle.setOdometer(vehicle.getOdometer() + trip.getActualDistance());

            vehicleRepository.save(vehicle);
            driverRepository.save(driver);
        }

        // Cancel trip

        if (previousStatus == TripStatus.DISPATCHED &&
                updatedTrip.getStatus() == TripStatus.CANCELLED) {

            vehicle.setStatus(VehicleStatus.AVAILABLE);
            driver.setStatus(DriverStatus.AVAILABLE);

            vehicleRepository.save(vehicle);
            driverRepository.save(driver);
        }

        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {

        Trip trip = tripRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with id " + id));

        tripRepository.delete(trip);
    }
}