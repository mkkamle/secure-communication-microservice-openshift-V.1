package com.cg.product.service;

import com.cg.product.dto.ProductRequest;
import com.cg.product.dto.ProductResponse;
import com.cg.product.entity.Product;
import com.cg.product.exception.InsufficientInventoryException;
import com.cg.product.exception.ProductNotFoundException;
import com.cg.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;

    public ProductResponse create(
            ProductRequest request) {

        Product product =
                Product.builder()
                        .name(request.name())
                        .price(request.price())
                        .quantity(request.quantity())
                        .build();

        return map(
                repository.save(product));
    }

    public ProductResponse getById(Long id) {

        return map(repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id)));
    }

    public List<ProductResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public ProductResponse update(
            Long id,
            ProductRequest request) {

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(id));

        product.setName(request.name());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());

        return map(repository.save(product));
    }

    public void delete(Long id) {

        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(id));

        repository.delete(product);
    }

    private ProductResponse map(
            Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity());
    }

    @Transactional
    public void reduceInventory(
            Long productId,
            Integer quantity) {

        Product product =
                repository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        productId));

        if(product.getQuantity()
                < quantity){

            throw new InsufficientInventoryException(
                    productId,
                    product.getQuantity(),
                    quantity);
        }

        product.setQuantity(
                product.getQuantity() - quantity);

        repository.save(product);
    }
}