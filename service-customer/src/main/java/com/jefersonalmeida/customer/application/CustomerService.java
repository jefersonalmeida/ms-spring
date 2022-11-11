package com.jefersonalmeida.customer.application;

import com.jefersonalmeida.customer.domain.Customer;
import com.jefersonalmeida.customer.infrastructure.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer save(final Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByDocument(final String document) {
        return customerRepository.findByDocument(document);
    }
}
