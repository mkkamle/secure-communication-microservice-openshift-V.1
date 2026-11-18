package com.cg.customer.controller;

import com.cg.customer.dto.CustomerRequest;
import com.cg.customer.dto.CustomerResponse;
import com.cg.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(
            @Valid @RequestBody
            CustomerRequest request) {

        return service.create(request);
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll() {

        return service.getAll();
    }

    @PutMapping("/{id}")
    public CustomerResponse update(
            @PathVariable Long id,
            @Valid @RequestBody
            CustomerRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }

    @GetMapping("/check")
    public String check(){
        return "checking";
    }
}