package com.jefersonalmeida.customer.infrastructure.repository;

import com.jefersonalmeida.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByDocument(String document);
}
