package com.jefersonalmeida.customer.application.representation;

import com.jefersonalmeida.customer.domain.Customer;

public record CustomerSaveRequest(
        String document,
        String name,
        Integer age
) {
    public Customer toEntity() {
        return new Customer(document, name, age);
    }
}
