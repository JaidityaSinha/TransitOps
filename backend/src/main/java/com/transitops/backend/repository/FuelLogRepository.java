package com.transitops.backend.repository;

import com.transitops.backend.entity.FuelLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelLogRepository extends JpaRepository<FuelLog, Long> {

    @Query("SELECT COALESCE(SUM(f.fuelCost), 0) FROM FuelLog f")
    Double getTotalFuelCost();

    @Query("SELECT COALESCE(SUM(f.liters), 0) FROM FuelLog f")
    Double getTotalFuelConsumed();

}