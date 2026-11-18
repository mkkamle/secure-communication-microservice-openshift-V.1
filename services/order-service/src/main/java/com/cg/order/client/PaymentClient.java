package com.cg.order.client;

import com.cg.order.dto.PaymentRequest;
import com.cg.order.dto.PaymentResponse;
import com.cg.order.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentClient {

    private final WebClient webClient;
    private final JwtTokenProvider tokenProvider;

    public PaymentClient(
            WebClient.Builder builder, @Value("${services.payment.url}")
            String paymentUrl, JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;

        this.webClient = builder
                .baseUrl(paymentUrl)
                .build();
    }

    public PaymentResponse pay(
            PaymentRequest request) {

        return webClient.post()
                .uri("/api/payments/process")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + tokenProvider.getToken()
                )
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
    }
}