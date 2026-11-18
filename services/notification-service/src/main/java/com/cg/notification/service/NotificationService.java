package com.cg.notification.service;

import com.cg.notification.dto.NotificationRequest;
import com.cg.notification.dto.NotificationResponse;
import com.cg.notification.entity.Notification;
import com.cg.notification.exception.NotificationNotFoundException;
import com.cg.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationResponse send(
            NotificationRequest request) {

        Notification notification =
                Notification.builder()
                        .recipient(
                                request.recipient())
                        .message(
                                request.message())
                        .status("SENT")
                        .build();

        return map(
                repository.save(notification));
    }

    public NotificationResponse getById(
            Long id) {

        Notification notification =
                repository.findById(id)
                        .orElseThrow(() ->
                                new NotificationNotFoundException(id));

        return map(notification);
    }

    public List<NotificationResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private NotificationResponse map(
            Notification notification) {

        return new NotificationResponse(
                notification.getId(),
                notification.getRecipient(),
                notification.getMessage(),
                notification.getStatus()
        );
    }
}