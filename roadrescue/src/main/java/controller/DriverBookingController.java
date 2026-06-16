package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.DriverBooking;
import com.roadrescue.roadrescue.repository.DriverBookingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class DriverBookingController {

    private final DriverBookingRepository repository;

    public DriverBookingController(
            DriverBookingRepository repository) {
        this.repository = repository;
    }

    // Create Booking
    @PostMapping
    public DriverBooking createBooking(
            @RequestBody DriverBooking booking) {

        booking.setStatus("BOOKED");
        return repository.save(booking);
    }

    // Get All Bookings
    @GetMapping
    public List<DriverBooking> getAllBookings() {
        return repository.findAll();
    }

    // Get Booking By Id
    @GetMapping("/{id}")
    public DriverBooking getBookingById(
            @PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    // Driver Accept Booking
    @PutMapping("/{bookingId}/accept/{driverId}")
    public DriverBooking acceptBooking(
            @PathVariable Long bookingId,
            @PathVariable Long driverId) {

        DriverBooking booking =
                repository.findById(bookingId).orElse(null);

        if (booking != null) {
            booking.setDriverId(driverId);
            booking.setStatus("ACCEPTED");
            return repository.save(booking);
        }

        return null;
    }

    // Complete Trip
    @PutMapping("/{bookingId}/complete")
    public DriverBooking completeBooking(
            @PathVariable Long bookingId) {

        DriverBooking booking =
                repository.findById(bookingId).orElse(null);

        if (booking != null) {
            booking.setStatus("COMPLETED");
            return repository.save(booking);
        }

        return null;
    }

    // Delete Booking
    @DeleteMapping("/{id}")
    public String deleteBooking(
            @PathVariable Long id) {

        repository.deleteById(id);
        return "Booking Deleted Successfully";
    }
}