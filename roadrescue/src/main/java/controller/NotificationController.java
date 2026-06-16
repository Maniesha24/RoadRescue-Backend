package com.roadrescue.roadrescue.controller;

import com.roadrescue.roadrescue.entity.Notification;
import com.roadrescue.roadrescue.repository.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository repository;

    public NotificationController(
            NotificationRepository repository) {

        this.repository = repository;
    }

    @PostMapping
    public Notification createNotification(
            @RequestBody Notification notification) {

        notification.setStatus("UNREAD");

        return repository.save(notification);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {

        return repository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(
            @PathVariable Long userId) {

        return repository.findByUserId(userId);
    }
}