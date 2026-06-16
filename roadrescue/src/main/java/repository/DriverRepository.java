package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}