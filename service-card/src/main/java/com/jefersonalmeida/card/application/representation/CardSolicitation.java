package com.jefersonalmeida.card.application.representation;

import java.math.BigDecimal;

public record CardSolicitation(Long id, String document, String address, BigDecimal limit) {
}
