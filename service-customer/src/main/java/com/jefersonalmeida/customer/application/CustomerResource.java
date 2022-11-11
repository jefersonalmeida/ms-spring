package com.jefersonalmeida.customer.application;

import com.jefersonalmeida.customer.application.representation.CustomerSaveRequest;
import com.jefersonalmeida.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping("customers")
public class CustomerResource {

    private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private final CustomerService customerService;

    public CustomerResource(final CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String status() {

        log.info("Obtendo o status do microserviço service-customer: {}", Instant.now());
        return "ok";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Customer> save(@RequestBody final CustomerSaveRequest request) {
        final var customer = customerService.save(request.toEntity());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();

        log.info("Customer created: {} - {}", location, customer);

        return ResponseEntity.created(URI.create("/customers/" + customer.getId())).body(customer);
    }

    @GetMapping(value = "{document}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> findByDocument(@PathVariable(name = "document") final String document) {
        final var customer = customerService.findByDocument(document);
        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(customer);
    }
}
