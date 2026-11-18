package com.cg.notification.dto;

import jakarta.validation.constraints.NotBlank;

public record NotificationRequest(

        @NotBlank
        String recipient,

        @NotBlank
        String message
) {
}