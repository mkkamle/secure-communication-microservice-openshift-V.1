package com.cg.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderRequest(

        @NotNull
        Long customerId,

        @NotNull
        Long productId,

        @NotNull
        @Min(value = 1,
                message = "Quantity must be greater than zero")
        Integer quantity
) {
}