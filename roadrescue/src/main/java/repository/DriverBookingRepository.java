package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.DriverBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverBookingRepository
        extends JpaRepository<DriverBooking, Long> {
}