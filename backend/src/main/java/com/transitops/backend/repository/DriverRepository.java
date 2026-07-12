package com.transitops.backend.repository;

import com.transitops.backend.entity.Driver;
import com.transitops.backend.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    long countByStatus(DriverStatus status);
}