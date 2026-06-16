package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Mechanic;
import com.roadrescue.roadrescue.repository.MechanicRepository;
import com.roadrescue.roadrescue.service.DistanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {

    private final MechanicRepository mechanicRepository;
    private final DistanceService distanceService;

    public MechanicController(
            MechanicRepository mechanicRepository,
            DistanceService distanceService) {

        this.mechanicRepository = mechanicRepository;
        this.distanceService = distanceService;
    }

    // Add Mechanic
    @PostMapping
    public Mechanic addMechanic(@RequestBody Mechanic mechanic) {

        if (mechanic.getAvailability() == null) {
            mechanic.setAvailability("AVAILABLE");
        }

        return mechanicRepository.save(mechanic);
    }

    // Get All Mechanics
    @GetMapping
    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    // Get Mechanic By Id
    @GetMapping("/{id}")
    public Mechanic getMechanicById(@PathVariable Long id) {
        return mechanicRepository.findById(id).orElse(null);
    }

    // Update Mechanic Location
    @PutMapping("/{id}/location")
    public Mechanic updateLocation(
            @PathVariable Long id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        Mechanic mechanic =
                mechanicRepository.findById(id).orElse(null);

        if (mechanic != null) {

            mechanic.setLatitude(latitude);
            mechanic.setLongitude(longitude);

            return mechanicRepository.save(mechanic);
        }

        return null;
    }

    // Get Mechanic Location
    @GetMapping("/{id}/location")
    public Mechanic getLocation(@PathVariable Long id) {

        return mechanicRepository.findById(id).orElse(null);
    }

    // Find Nearest Mechanic
    @GetMapping("/nearest")
    public Mechanic findNearestMechanic(
            @RequestParam Double lat,
            @RequestParam Double lon) {

        List<Mechanic> mechanics =
                mechanicRepository.findAll();

        Mechanic nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Mechanic mechanic : mechanics) {

            if (mechanic.getLatitude() == null ||
                    mechanic.getLongitude() == null) {
                continue;
            }

            double distance =
                    distanceService.calculateDistance(
                            lat,
                            lon,
                            mechanic.getLatitude(),
                            mechanic.getLongitude());

            if (distance < minDistance) {
                minDistance = distance;
                nearest = mechanic;
            }
        }

        return nearest;
    }

    // Delete Mechanic
    @DeleteMapping("/{id}")
    public String deleteMechanic(@PathVariable Long id) {

        mechanicRepository.deleteById(id);

        return "Mechanic Deleted Successfully";
    }
}