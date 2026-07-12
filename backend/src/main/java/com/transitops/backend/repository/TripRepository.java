package com.transitops.backend.repository;

import com.transitops.backend.entity.Trip;
import com.transitops.backend.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {

    long countByStatus(TripStatus status);
}