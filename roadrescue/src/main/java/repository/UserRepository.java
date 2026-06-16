package com.roadrescue.roadrescue.repository;

import com.roadrescue.roadrescue.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}