package com.transitops.backend.repository;

import com.transitops.backend.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {

    @Query("SELECT COALESCE(SUM(m.cost), 0) FROM MaintenanceLog m")
    Double getTotalMaintenanceCost();

}