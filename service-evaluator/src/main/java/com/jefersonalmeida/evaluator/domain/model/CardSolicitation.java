package com.jefersonalmeida.evaluator.domain.model;

import java.math.BigDecimal;

public record CardSolicitation(Long id, String document, String address, BigDecimal limit) {
}
