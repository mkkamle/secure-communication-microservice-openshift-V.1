package com.cg.order.client;

import com.cg.order.dto.NotificationRequest;
import com.cg.order.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NotificationClient {

    private final WebClient webClient;
    private final JwtTokenProvider tokenProvider;


    public NotificationClient(
            WebClient.Builder builder, @Value("${services.notification.url}")
            String notificationUrl, JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.webClient = builder
                .baseUrl(notificationUrl)
                .build();
    }

    public void send(
            NotificationRequest request) {

        webClient.post()
                .uri("/api/notifications/send")
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + tokenProvider.getToken()
                )
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
