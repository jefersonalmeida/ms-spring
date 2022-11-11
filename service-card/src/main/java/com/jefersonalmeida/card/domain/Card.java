package com.jefersonalmeida.card.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Card")
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flag")
    @Enumerated(EnumType.STRING)
    private CardFlag flag;

    @Column(name = "name")
    private String name;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "card_limit")
    private BigDecimal cardLimit;

    public Card() {
    }

    public Card(
            final CardFlag flag,
            final String name,
            final BigDecimal income,
            final BigDecimal cardLimit
    ) {
        this.flag = flag;
        this.name = name;
        this.income = income;
        this.cardLimit = cardLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CardFlag getFlag() {
        return flag;
    }

    public void setFlag(CardFlag brand) {
        this.flag = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }
}
