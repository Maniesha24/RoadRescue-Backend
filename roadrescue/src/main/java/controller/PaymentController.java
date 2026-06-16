package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Payment;
import com.roadrescue.roadrescue.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(
            PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public Payment createPayment(
            @RequestBody Payment payment) {

        payment.setPaymentStatus("PAID");

        payment.setTransactionId(
                UUID.randomUUID().toString());

        return paymentRepository.save(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {

        return paymentRepository.findAll();
    }

    @GetMapping("/request/{requestId}")
    public List<Payment> getPaymentByRequest(
            @PathVariable Long requestId) {

        return paymentRepository
                .findByRequestId(requestId);
    }
}