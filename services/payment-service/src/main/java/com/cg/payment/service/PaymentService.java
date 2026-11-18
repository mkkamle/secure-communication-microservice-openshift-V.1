package com.cg.payment.service;

import com.cg.payment.dto.PaymentRequest;
import com.cg.payment.dto.PaymentResponse;
import com.cg.payment.entity.Payment;
import com.cg.payment.exception.PaymentNotFoundException;
import com.cg.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentResponse process(
            PaymentRequest request) {

        Payment payment =
                Payment.builder()
                        .orderId(request.orderId())
                        .amount(request.amount())
                        .status("SUCCESS")
                        .build();

        return map(repository.save(payment));
    }

    public PaymentResponse getById(Long id) {

        return map(
                repository.findById(id)
                        .orElseThrow(() ->
                                new PaymentNotFoundException(id)));
    }

    public List<PaymentResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private PaymentResponse map(
            Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus()
        );
    }
}