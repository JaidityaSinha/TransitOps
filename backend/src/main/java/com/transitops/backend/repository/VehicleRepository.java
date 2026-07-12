package com.transitops.backend.repository;

import com.transitops.backend.entity.Vehicle;
import com.transitops.backend.enums.VehicleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    boolean existsByRegistrationNumber(String registrationNumber);

    long countByStatus(VehicleStatus status);
}