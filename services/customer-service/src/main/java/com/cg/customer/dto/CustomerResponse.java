package com.cg.customer.dto;

public record CustomerResponse(
        Long id,
        String name,
        String email
) {
}