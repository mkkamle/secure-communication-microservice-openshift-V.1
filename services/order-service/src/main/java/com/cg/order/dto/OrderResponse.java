package com.cg.order.dto;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        Long customerId,
        Long productId,
        Integer quantity,
        BigDecimal totalAmount,
        String status
) {
}