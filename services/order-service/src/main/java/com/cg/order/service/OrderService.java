package com.cg.order.service;

import com.cg.order.client.CustomerClient;
import com.cg.order.client.NotificationClient;
import com.cg.order.client.PaymentClient;
import com.cg.order.client.ProductClient;
import com.cg.order.dto.*;
import com.cg.order.entity.IdempotencyRecord;
import com.cg.order.entity.Order;
import com.cg.order.exception.InsufficientInventoryException;
import com.cg.order.exception.InvalidOrderException;
import com.cg.order.repository.IdempotencyRepository;
import com.cg.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;
    private final IdempotencyRepository idempotencyRepository;

    public OrderResponse createOrder(
            OrderRequest request, String idempotencyKey) {
        if (idempotencyKey == null ||
                idempotencyKey.isBlank()) {

            throw new InvalidOrderException(
                    "X-Idempotency-Key is mandatory");
        }
        Optional<IdempotencyRecord> record =

                idempotencyRepository
                        .findByIdempotencyKey(
                                idempotencyKey);

        if(record.isPresent()) {

            Order order =
                    repository.findById(
                                    record.get()
                                            .getOrderId())
                            .orElseThrow();

            return map(order);
        }

        if (request.quantity() <= 0) {

            throw new InvalidOrderException(
                    "Quantity must be greater than zero");
        }

        CustomerResponse customer =
                customerClient.getCustomer(
                        request.customerId());

        ProductResponse product =
                productClient.getProduct(
                        request.productId());

        if (product.quantity()
                < request.quantity()) {

            throw new InsufficientInventoryException(
                    product.id(),
                    product.quantity(),
                    request.quantity());
        }

        BigDecimal totalAmount =
                product.price().multiply(
                        BigDecimal.valueOf(
                                request.quantity()));

        paymentClient.pay(
                new PaymentRequest(
                        null,
                        totalAmount));
        System.out.println(
                "Reducing inventory for product "
                        + request.productId());
        productClient.reduceInventory(
                request.productId(),
                request.quantity());

        Order order =
                Order.builder()
                        .customerId(request.customerId())
                        .productId(request.productId())
                        .quantity(request.quantity())
                        .totalAmount(totalAmount)
                        .status("CREATED")
                        .build();

        Order saved =
                repository.save(order);
        idempotencyRepository.save(

                IdempotencyRecord.builder()

                        .idempotencyKey(
                                idempotencyKey)

                        .orderId(
                                saved.getId())

                        .build()
        );

        notificationClient.send(
                new NotificationRequest(
                        customer.email(),
                        "Order created successfully"));

        return map(saved);
    }

    public List<OrderResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    private OrderResponse map(
            Order order) {

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }
}