package com.cg.product.controller;

import com.cg.product.dto.InventoryUpdateRequest;
import com.cg.product.dto.ProductRequest;
import com.cg.product.dto.ProductResponse;
import com.cg.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @Valid
            @RequestBody ProductRequest request) {

        return service.create(request);
    }

    @PreAuthorize(
            "hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}")
    public ProductResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll() {

        return service.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ProductResponse update(
            @PathVariable Long id,
            @Valid
            @RequestBody ProductRequest request) {

        return service.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }

    @PutMapping("/{id}/inventory")
    public void reduceInventory(
            @PathVariable Long id,
            @RequestBody
            InventoryUpdateRequest request) {

        service.reduceInventory(
                id,
                request.quantity());
    }
}