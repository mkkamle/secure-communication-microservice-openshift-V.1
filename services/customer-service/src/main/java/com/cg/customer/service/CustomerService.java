package com.cg.customer.service;

import com.cg.customer.dto.CustomerRequest;
import com.cg.customer.dto.CustomerResponse;
import com.cg.customer.entity.Customer;
import com.cg.customer.exception.CustomerNotFoundException;
import com.cg.customer.repository.CustomerRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerResponse create(
            CustomerRequest request) {

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .build();

        Customer saved =
                repository.save(customer);

        return map(saved);
    }

    public CustomerResponse getById(Long id) {

        Customer customer =
                repository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException(id));

        return map(customer);
    }

    public List<CustomerResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public CustomerResponse update(
            Long id,
            CustomerRequest request) {

        Customer customer =
                repository.findById(id)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(id));

        customer.setName(request.name());
        customer.setEmail(request.email());

        return map(repository.save(customer));
    }

    public void delete(Long id) {

        Customer customer =
                repository.findById(id)
                        .orElseThrow(() ->
                                new CustomerNotFoundException(id));

        repository.delete(customer);
    }

    private CustomerResponse map(
            Customer customer) {

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail());
    }
}