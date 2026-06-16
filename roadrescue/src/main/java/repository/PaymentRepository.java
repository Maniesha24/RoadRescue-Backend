package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    List<Payment> findByRequestId(Long requestId);
}