package com.cg.order.client;

import com.cg.order.dto.CustomerResponse;
import com.cg.order.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerClient {

    private final WebClient webClient;
    private final JwtTokenProvider tokenProvider;

    public CustomerClient(
            WebClient.Builder builder,
            JwtTokenProvider tokenProvider,
            @Value("${services.customer.url}")
            String customerUrl) {

        this.tokenProvider = tokenProvider;

        this.webClient = builder
                .baseUrl(customerUrl)
                .build();
    }

    public CustomerResponse getCustomer(Long id) {

        System.out.println(
                "JWT Token = "
                        + tokenProvider.getToken());

        return webClient
                .get()
                .uri("/api/customers/{id}", id)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer "
                                + tokenProvider.getToken())
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();
    }
}