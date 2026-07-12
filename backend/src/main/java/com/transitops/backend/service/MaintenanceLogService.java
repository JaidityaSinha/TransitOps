package com.transitops.backend.service;

import com.transitops.backend.entity.MaintenanceLog;
import com.transitops.backend.entity.Vehicle;
import com.transitops.backend.enums.MaintenanceStatus;
import com.transitops.backend.enums.VehicleStatus;
import com.transitops.backend.exception.ResourceNotFoundException;
import com.transitops.backend.repository.MaintenanceLogRepository;
import com.transitops.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository repository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceLogService(MaintenanceLogRepository repository,
                                 VehicleRepository vehicleRepository) {
        this.repository = repository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<MaintenanceLog> getAllLogs() {
        return repository.findAll();
    }

    public MaintenanceLog getLogById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance log not found with id " + id));
    }

    public MaintenanceLog saveLog(MaintenanceLog log) {

        Vehicle vehicle = vehicleRepository.findById(log.getVehicle().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vehicle not found"));

        if (log.getStatus() == MaintenanceStatus.ACTIVE) {
            vehicle.setStatus(VehicleStatus.IN_SHOP);
            vehicleRepository.save(vehicle);
        }

        return repository.save(log);
    }

    public MaintenanceLog updateLog(Long id, MaintenanceLog updatedLog) {

        MaintenanceLog log = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance log not found with id " + id));

        MaintenanceStatus previousStatus = log.getStatus();

        log.setServiceType(updatedLog.getServiceType());
        log.setCost(updatedLog.getCost());
        log.setServiceDate(updatedLog.getServiceDate());

        log.setCompletionDate(updatedLog.getCompletionDate());
        log.setRemarks(updatedLog.getRemarks());

        log.setStatus(updatedLog.getStatus());

        Vehicle vehicle = log.getVehicle();

        if (previousStatus == MaintenanceStatus.ACTIVE &&
                updatedLog.getStatus() == MaintenanceStatus.COMPLETED) {

            if (vehicle.getStatus() != VehicleStatus.RETIRED) {

                vehicle.setStatus(VehicleStatus.AVAILABLE);
                vehicleRepository.save(vehicle);
            }
        }

        return repository.save(log);
    }

    public void deleteLog(Long id) {

        MaintenanceLog log = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance log not found with id " + id));

        repository.delete(log);
    }
}