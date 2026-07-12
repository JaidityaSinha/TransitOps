package com.transitops.backend.service;

import com.transitops.backend.entity.FuelLog;
import com.transitops.backend.exception.ResourceNotFoundException;
import com.transitops.backend.repository.FuelLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelLogService {

    private final FuelLogRepository repository;

    public FuelLogService(FuelLogRepository repository) {
        this.repository = repository;
    }

    public List<FuelLog> getAllFuelLogs() {
        return repository.findAll();
    }

    public FuelLog getFuelLogById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Fuel log not found with id " + id));
    }

    public FuelLog saveFuelLog(FuelLog fuelLog) {
        return repository.save(fuelLog);
    }

    public FuelLog updateFuelLog(Long id, FuelLog updatedFuelLog) {

        FuelLog fuelLog = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Fuel log not found with id " + id));

        fuelLog.setVehicle(updatedFuelLog.getVehicle());
        fuelLog.setLiters(updatedFuelLog.getLiters());
        fuelLog.setFuelCost(updatedFuelLog.getFuelCost());
        fuelLog.setFuelDate(updatedFuelLog.getFuelDate());

        // NEW FIELD
        fuelLog.setOdometerReading(updatedFuelLog.getOdometerReading());

        return repository.save(fuelLog);
    }

    public void deleteFuelLog(Long id) {

        FuelLog fuelLog = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Fuel log not found with id " + id));

        repository.delete(fuelLog);
    }
}