package com.jefersonalmeida.evaluator.infrastructure.client;

import com.jefersonalmeida.evaluator.domain.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-customer", path = "customers")
public interface CustomerResourceClient {

    @GetMapping(value = "{document}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Customer> findByDocument(@PathVariable(name = "document") final String document);
}
