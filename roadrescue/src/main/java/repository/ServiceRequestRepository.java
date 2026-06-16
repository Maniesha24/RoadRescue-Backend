package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository
        extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findByStatus(String status);
}