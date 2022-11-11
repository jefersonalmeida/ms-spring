package com.jefersonalmeida.evaluator.domain.model;

import java.math.BigDecimal;

public record Card(String name, String flag, BigDecimal cardLimit) {
}
