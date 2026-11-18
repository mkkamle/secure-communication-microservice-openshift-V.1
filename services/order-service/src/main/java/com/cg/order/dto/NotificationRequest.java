package com.cg.order.dto;

public record NotificationRequest(
        String recipient,
        String message
) {
}