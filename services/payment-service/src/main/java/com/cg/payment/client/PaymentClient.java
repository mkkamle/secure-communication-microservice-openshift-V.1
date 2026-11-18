package com.cg.payment.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class PaymentClient {
//
//    private final WebClient webClient;
//
//    public PaymentResponse pay(
//          PaymentRequest request) {
//
//        return webClient.post()
//                .uri(
//                  "http://localhost:8083/api/payments"
//                )
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(
//                     PaymentResponse.class)
//                .block();
//    }
//}