package com.transitops.backend.service;

import com.transitops.backend.entity.Trip;
import com.transitops.backend.repository.ExpenseRepository;
import com.transitops.backend.repository.FuelLogRepository;
import com.transitops.backend.repository.MaintenanceLogRepository;
import com.transitops.backend.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final FuelLogRepository fuelRepository;
    private final MaintenanceLogRepository maintenanceRepository;
    private final ExpenseRepository expenseRepository;
    private final TripRepository tripRepository;

    public AnalyticsService(
            FuelLogRepository fuelRepository,
            MaintenanceLogRepository maintenanceRepository,
            ExpenseRepository expenseRepository,
            TripRepository tripRepository) {

        this.fuelRepository = fuelRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.expenseRepository = expenseRepository;
        this.tripRepository = tripRepository;
    }

    public Map<String, Object> getAnalytics() {

        Map<String, Object> analytics = new HashMap<>();

        Double fuelCost = fuelRepository.getTotalFuelCost();
        Double fuelConsumed = fuelRepository.getTotalFuelConsumed();
        Double maintenanceCost = maintenanceRepository.getTotalMaintenanceCost();
        Double expenses = expenseRepository.getTotalExpenses();

        List<Trip> trips = tripRepository.findAll();

        double totalDistance = 0.0;
        double totalRevenue = 0.0;

        for (Trip trip : trips) {

            if (trip.getActualDistance() != null)
                totalDistance += trip.getActualDistance();

            if (trip.getRevenue() != null)
                totalRevenue += trip.getRevenue();
        }

        double fuelEfficiency =
                fuelConsumed == 0 ? 0 : totalDistance / fuelConsumed;

        double operationalCost =
                fuelCost + maintenanceCost + expenses;

        analytics.put("totalTrips", tripRepository.count());

        analytics.put("totalDistance", totalDistance);

        analytics.put("totalRevenue", totalRevenue);

        analytics.put("fuelEfficiency", fuelEfficiency);

        analytics.put("totalFuelCost", fuelCost);

        analytics.put("totalFuelConsumed", fuelConsumed);

        analytics.put("totalMaintenanceCost", maintenanceCost);

        analytics.put("totalExpenses", expenses);

        analytics.put("totalOperationalCost", operationalCost);

        analytics.put("netProfit", totalRevenue - operationalCost);

        return analytics;
    }
}