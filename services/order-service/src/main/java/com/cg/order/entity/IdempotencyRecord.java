package com.cg.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "idempotency_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyRecord {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true)
    private String idempotencyKey;

    @Column(nullable = false)
    private Long orderId;
}