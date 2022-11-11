package com.jefersonalmeida.card.application.representation;

import com.jefersonalmeida.card.domain.CardCustomer;

import java.math.BigDecimal;

public record CardCustomerResponse(String name, String flag, BigDecimal cardLimit) {
    public static CardCustomerResponse fromModel(CardCustomer cardCustomer) {
        return new CardCustomerResponse(
                cardCustomer.getCard().getName(),
                cardCustomer.getCard().getFlag().toString(),
                cardCustomer.getCardLimit()
        );
    }
}
