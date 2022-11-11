package com.jefersonalmeida.evaluator.domain.model;

import java.util.List;

public class CustomerSituation {
    private final Customer customer;
    private final List<Card> cards;

    private CustomerSituation(Builder builder) {
        customer = builder.customer;
        cards = builder.cards;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Card> getCards() {
        return cards;
    }

    public static final class Builder {
        private Customer customer;
        private List<Card> cards;

        private Builder() {
        }

        public Builder customer(Customer val) {
            customer = val;
            return this;
        }

        public Builder cards(List<Card> val) {
            cards = val;
            return this;
        }

        public CustomerSituation build() {
            return new CustomerSituation(this);
        }
    }
}
