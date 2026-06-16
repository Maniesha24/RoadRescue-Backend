package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.dto.LoginRequest;
import com.roadrescue.roadrescue.dto.RegisterRequest;
import com.roadrescue.roadrescue.entity.User;
import com.roadrescue.roadrescue.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.roadrescue.roadrescue.security.JwtUtil;
import com.roadrescue.roadrescue.dto.JwtResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public String test() {
        return "Auth Controller Working";
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return new JwtResponse("User Not Found");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return new JwtResponse("Invalid Password");
        }

        String token = JwtUtil.generateToken(user.getEmail());

        return new JwtResponse(token);
    }
}