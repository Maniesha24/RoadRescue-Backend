package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Driver;
import com.roadrescue.roadrescue.repository.DriverRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverRepository repository;

    public DriverController(DriverRepository repository) {
        this.repository = repository;
    }

    // Add Driver
    @PostMapping
    public Driver addDriver(@RequestBody Driver driver) {

        if (driver.getAvailability() == null) {
            driver.setAvailability("AVAILABLE");
        }

        return repository.save(driver);
    }

    // Get All Drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return repository.findAll();
    }

    // Get Driver By ID
    @GetMapping("/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Update Driver Location
    @PutMapping("/{id}/location")
    public Driver updateLocation(
            @PathVariable Long id,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        Driver driver = repository.findById(id).orElse(null);

        if (driver != null) {
            driver.setLatitude(latitude);
            driver.setLongitude(longitude);

            return repository.save(driver);
        }

        return null;
    }

    // Get Driver Location
    @GetMapping("/{id}/location")
    public Driver getLocation(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // Delete Driver
    @DeleteMapping("/{id}")
    public String deleteDriver(@PathVariable Long id) {

        repository.deleteById(id);

        return "Driver Deleted Successfully";
    }
}