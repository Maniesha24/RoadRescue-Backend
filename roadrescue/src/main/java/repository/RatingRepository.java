package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository
        extends JpaRepository<Rating, Long> {

    List<Rating> findByMechanicId(Long mechanicId);
}