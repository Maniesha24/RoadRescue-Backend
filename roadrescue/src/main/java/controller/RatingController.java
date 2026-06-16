package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Rating;
import com.roadrescue.roadrescue.repository.RatingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(
            RatingRepository ratingRepository) {

        this.ratingRepository = ratingRepository;
    }

    @PostMapping
    public Rating addRating(
            @RequestBody Rating rating) {

        return ratingRepository.save(rating);
    }

    @GetMapping
    public List<Rating> getAllRatings() {

        return ratingRepository.findAll();
    }

    @GetMapping("/mechanic/{id}")
    public List<Rating> getMechanicRatings(
            @PathVariable Long id) {

        return ratingRepository.findByMechanicId(id);
    }
}