package com.transitops.backend.service;

import com.transitops.backend.enums.DriverStatus;
import com.transitops.backend.enums.TripStatus;
import com.transitops.backend.enums.VehicleStatus;
import com.transitops.backend.repository.DriverRepository;
import com.transitops.backend.repository.MaintenanceLogRepository;
import com.transitops.backend.repository.TripRepository;
import com.transitops.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final MaintenanceLogRepository maintenanceRepository;

    public DashboardService(
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            TripRepository tripRepository,
            MaintenanceLogRepository maintenanceRepository) {

        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.maintenanceRepository = maintenanceRepository;
    }

    public Map<String, Object> getDashboardSummary() {

        Map<String, Object> dashboard = new HashMap<>();

        long totalVehicles = vehicleRepository.count();
        long availableVehicles = vehicleRepository.countByStatus(VehicleStatus.AVAILABLE);
        long inShopVehicles = vehicleRepository.countByStatus(VehicleStatus.IN_SHOP);

        long totalDrivers = driverRepository.count();
        long availableDrivers = driverRepository.countByStatus(DriverStatus.AVAILABLE);
        long driversOnDuty = driverRepository.countByStatus(DriverStatus.ON_TRIP);

        long activeTrips = tripRepository.countByStatus(TripStatus.DISPATCHED);
        long completedTrips = tripRepository.countByStatus(TripStatus.COMPLETED);
        long pendingTrips = tripRepository.countByStatus(TripStatus.DRAFT);

        dashboard.put("totalVehicles", totalVehicles);
        dashboard.put("availableVehicles", availableVehicles);
        dashboard.put("vehiclesInMaintenance", inShopVehicles);

        dashboard.put("totalDrivers", totalDrivers);
        dashboard.put("availableDrivers", availableDrivers);
        dashboard.put("driversOnDuty", driversOnDuty);

        dashboard.put("activeTrips", activeTrips);
        dashboard.put("completedTrips", completedTrips);
        dashboard.put("pendingTrips", pendingTrips);

        dashboard.put("maintenanceLogs", maintenanceRepository.count());

        double fleetUtilization = totalVehicles == 0
                ? 0
                : (driversOnDuty * 100.0) / totalVehicles;

        dashboard.put("fleetUtilization", fleetUtilization);

        return dashboard;
    }
}