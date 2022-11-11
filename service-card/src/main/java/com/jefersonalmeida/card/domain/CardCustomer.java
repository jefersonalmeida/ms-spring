package com.jefersonalmeida.card.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "CardCustomer")
@Table(name = "cards_customers")
public class CardCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document")
    private String document;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Column(name = "card_limit")
    private BigDecimal cardLimit;

    public CardCustomer() {
    }

    public CardCustomer(final String document, final Card card, final BigDecimal cardLimit) {
        this.document = document;
        this.card = card;
        this.cardLimit = cardLimit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }
}
