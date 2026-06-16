package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}