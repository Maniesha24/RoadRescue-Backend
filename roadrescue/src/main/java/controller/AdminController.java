package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Rating;
import com.roadrescue.roadrescue.entity.ServiceRequest;
import com.roadrescue.roadrescue.repository.DriverRepository;
import com.roadrescue.roadrescue.repository.MechanicRepository;
import com.roadrescue.roadrescue.repository.RatingRepository;
import com.roadrescue.roadrescue.repository.ServiceRequestRepository;
import com.roadrescue.roadrescue.repository.UserRepository;
import com.roadrescue.roadrescue.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final MechanicRepository mechanicRepository;
    private final DriverRepository driverRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final RatingRepository ratingRepository;

    public AdminController(
            UserRepository userRepository,
            VehicleRepository vehicleRepository,
            MechanicRepository mechanicRepository,
            DriverRepository driverRepository,
            ServiceRequestRepository serviceRequestRepository,
            RatingRepository ratingRepository) {

        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.mechanicRepository = mechanicRepository;
        this.driverRepository = driverRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.ratingRepository = ratingRepository;
    }

    // Dashboard Statistics
    @GetMapping("/dashboard")
    public Map<String, Long> getDashboard() {

        Map<String, Long> dashboard = new HashMap<>();

        dashboard.put("totalUsers", userRepository.count());
        dashboard.put("totalVehicles", vehicleRepository.count());
        dashboard.put("totalMechanics", mechanicRepository.count());
        dashboard.put("totalDrivers", driverRepository.count());
        dashboard.put("totalRequests", serviceRequestRepository.count());

        return dashboard;
    }

    // View All Completed Services
    @GetMapping("/completed-services")
    public List<ServiceRequest> getCompletedServices() {

        return serviceRequestRepository.findByStatus("COMPLETED");
    }

    // Mechanic Performance Dashboard
    @GetMapping("/mechanic-performance/{mechanicId}")
    public Map<String, Object> mechanicPerformance(
            @PathVariable Long mechanicId) {

        List<Rating> ratings =
                ratingRepository.findByMechanicId(mechanicId);

        double averageRating = 0;

        if (!ratings.isEmpty()) {

            int total = 0;

            for (Rating rating : ratings) {
                total += rating.getRating();
            }

            averageRating =
                    (double) total / ratings.size();
        }

        Map<String, Object> response =
                new HashMap<>();

        response.put("mechanicId", mechanicId);
        response.put("totalRatings", ratings.size());
        response.put("averageRating", averageRating);

        return response;
    }
}