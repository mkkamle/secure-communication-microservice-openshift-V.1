package com.cg.notification.dto;

public record NotificationResponse(
        Long id,
        String recipient,
        String message,
        String status
) {
}