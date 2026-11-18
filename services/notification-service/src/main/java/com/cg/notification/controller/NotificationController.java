package com.cg.notification.controller;

import com.cg.notification.dto.NotificationRequest;
import com.cg.notification.dto.NotificationResponse;
import com.cg.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponse send(
            @Valid @RequestBody
            NotificationRequest request) {

        return service.send(request);
    }

    @GetMapping("/{id}")
    public NotificationResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public List<NotificationResponse> getAll() {

        return service.getAll();
    }
}