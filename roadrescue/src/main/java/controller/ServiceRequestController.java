package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Mechanic;
import com.roadrescue.roadrescue.entity.ServiceRequest;
import com.roadrescue.roadrescue.repository.MechanicRepository;
import com.roadrescue.roadrescue.repository.ServiceRequestRepository;
import com.roadrescue.roadrescue.service.DistanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
public class ServiceRequestController {

    private final ServiceRequestRepository repository;
    private final MechanicRepository mechanicRepository;
    private final DistanceService distanceService;

    public ServiceRequestController(
            ServiceRequestRepository repository,
            MechanicRepository mechanicRepository,
            DistanceService distanceService) {

        this.repository = repository;
        this.mechanicRepository = mechanicRepository;
        this.distanceService = distanceService;
    }

    // Create Request & Auto Assign Nearest Mechanic
    @PostMapping
    public ServiceRequest createRequest(
            @RequestBody ServiceRequest request) {

        List<Mechanic> mechanics = mechanicRepository.findAll();

        Mechanic nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Mechanic mechanic : mechanics) {

            if (mechanic.getLatitude() == null ||
                    mechanic.getLongitude() == null) {
                continue;
            }

            double distance =
                    distanceService.calculateDistance(
                            request.getCustomerLatitude(),
                            request.getCustomerLongitude(),
                            mechanic.getLatitude(),
                            mechanic.getLongitude());

            if (distance < minDistance) {
                minDistance = distance;
                nearest = mechanic;
            }
        }

        if (nearest != null) {
            request.setMechanicId(nearest.getId());
            request.setStatus("ASSIGNED");
        } else {
            request.setStatus("PENDING");
        }

        return repository.save(request);
    }

    // Get All Requests
    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    // Get Request By Id
    @GetMapping("/{id}")
    public ServiceRequest getRequestById(
            @PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    // Mechanic Accept Request
    @PutMapping("/{requestId}/accept/{mechanicId}")
    public ServiceRequest acceptRequest(
            @PathVariable Long requestId,
            @PathVariable Long mechanicId) {

        ServiceRequest request =
                repository.findById(requestId).orElse(null);

        if (request != null) {

            request.setMechanicId(mechanicId);
            request.setStatus("ACCEPTED");

            return repository.save(request);
        }

        return null;
    }

    // Mechanic On The Way
    @PutMapping("/{id}/on-the-way")
    public ServiceRequest onTheWay(
            @PathVariable Long id) {

        ServiceRequest request =
                repository.findById(id).orElse(null);

        if (request != null) {

            request.setStatus("ON_THE_WAY");

            return repository.save(request);
        }

        return null;
    }

    // Mechanic Arrived
    @PutMapping("/{id}/arrived")
    public ServiceRequest arrived(
            @PathVariable Long id) {

        ServiceRequest request =
                repository.findById(id).orElse(null);

        if (request != null) {

            request.setStatus("ARRIVED");

            return repository.save(request);
        }

        return null;
    }

    // Service Started
    @PutMapping("/{id}/start")
    public ServiceRequest startService(
            @PathVariable Long id) {

        ServiceRequest request =
                repository.findById(id).orElse(null);

        if (request != null) {

            request.setStatus("IN_PROGRESS");

            return repository.save(request);
        }

        return null;
    }

    // Complete Service
    @PutMapping("/{id}/complete")
    public ServiceRequest completeRequest(
            @PathVariable Long id) {

        ServiceRequest request =
                repository.findById(id).orElse(null);

        if (request != null) {

            request.setStatus("COMPLETED");

            return repository.save(request);
        }

        return null;
    }

    // Cancel Service Request
    @PutMapping("/{id}/cancel")
    public ServiceRequest cancelRequest(
            @PathVariable Long id) {

        ServiceRequest request =
                repository.findById(id).orElse(null);

        if (request != null) {

            request.setStatus("CANCELLED");

            return repository.save(request);
        }

        return null;
    }

    // Delete Request
    @DeleteMapping("/{id}")
    public String deleteRequest(
            @PathVariable Long id) {

        repository.deleteById(id);

        return "Service Request Deleted Successfully";
    }
}