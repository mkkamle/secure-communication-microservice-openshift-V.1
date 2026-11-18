package com.cg.order.client;

import com.cg.order.dto.InventoryUpdateRequest;
import com.cg.order.dto.ProductResponse;
import com.cg.order.exception.ProductNotFoundException;
import com.cg.order.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ProductClient {

    private final WebClient webClient;
    private final JwtTokenProvider tokenProvider;

    public ProductClient(WebClient.Builder builder, @Value("${services.product.url}")
    String productUrl, JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;

        this.webClient = builder
                .baseUrl(productUrl)
                .build();
    }

    public ProductResponse getProduct(Long id) {

        try {
            return webClient
                    .get()
                    .uri("/api/products/{id}", id)
                    .header(
                    HttpHeaders.AUTHORIZATION,
                    "Bearer " + tokenProvider.getToken()
            )
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

        } catch (WebClientResponseException.NotFound ex) {

            throw new ProductNotFoundException(id);
        }
    }
    public void reduceInventory(
            Long productId,
            Integer quantity) {

        webClient.put()

                .uri(
                        "/api/products/{id}/inventory",
                        productId)

                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer "
                                + tokenProvider.getToken())

                .bodyValue(
                        new InventoryUpdateRequest(
                                quantity))

                .retrieve()

                .toBodilessEntity()

                .block();
    }
}