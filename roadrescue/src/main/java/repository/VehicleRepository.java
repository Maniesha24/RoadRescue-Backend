package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}