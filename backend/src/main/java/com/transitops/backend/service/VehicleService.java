package com.transitops.backend.service;

import com.transitops.backend.entity.Vehicle;
import com.transitops.backend.exception.ResourceNotFoundException;
import com.transitops.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id " + id));
    }

    public Vehicle saveVehicle(Vehicle vehicle) {

        if (vehicleRepository.existsByRegistrationNumber(vehicle.getRegistrationNumber())) {
            throw new IllegalArgumentException("Vehicle registration number already exists.");
        }

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id " + id));

        vehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
        vehicle.setVehicleName(updatedVehicle.getVehicleName());
        vehicle.setVehicleType(updatedVehicle.getVehicleType());
        vehicle.setMaxLoadCapacity(updatedVehicle.getMaxLoadCapacity());
        vehicle.setOdometer(updatedVehicle.getOdometer());
        vehicle.setAcquisitionCost(updatedVehicle.getAcquisitionCost());
        vehicle.setStatus(updatedVehicle.getStatus());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found with id " + id));

        vehicleRepository.delete(vehicle);
    }
}