package com.jefersonalmeida.evaluator.domain.model;

import java.math.BigDecimal;

public record CardResponse(Long id, String flag, String name, BigDecimal income, BigDecimal cardLimit) {
}
