package com.cg.order.controller;

import com.cg.order.dto.OrderRequest;
import com.cg.order.dto.OrderResponse;
import com.cg.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(
            @RequestHeader(
                    "X-Idempotency-Key")
            String idempotencyKey,
            @RequestBody
            OrderRequest request) {

        return service.createOrder(request, idempotencyKey);
    }

    @GetMapping
    public List<OrderResponse> getAll() {

        return service.getAll();
    }
}