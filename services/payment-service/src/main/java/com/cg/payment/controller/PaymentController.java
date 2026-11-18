package com.cg.payment.controller;

import com.cg.payment.dto.PaymentRequest;
import com.cg.payment.dto.PaymentResponse;
import com.cg.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/process")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse process(
            @Valid @RequestBody
            PaymentRequest request) {

        return service.process(request);
    }

    @GetMapping("/{id}")
    public PaymentResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public List<PaymentResponse> getAll() {

        return service.getAll();
    }
}