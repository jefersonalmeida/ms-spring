package com.jefersonalmeida.card.application.representation;

import com.jefersonalmeida.card.domain.Card;
import com.jefersonalmeida.card.domain.CardFlag;

import java.math.BigDecimal;

public record CardSaveRequest(
        CardFlag flag,
        String name,
        BigDecimal income,
        BigDecimal cardLimit
) {
    public Card toEntity() {
        return new Card(flag, name, income, cardLimit);
    }
}
