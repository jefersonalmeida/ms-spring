package com.jefersonalmeida.card.application;

import com.jefersonalmeida.card.domain.CardCustomer;
import com.jefersonalmeida.card.infrastructure.repository.CardCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardCustomerService {

    private final CardCustomerRepository cardCustomerRepository;

    public CardCustomerService(final CardCustomerRepository cardCustomerRepository) {
        this.cardCustomerRepository = cardCustomerRepository;
    }

    public List<CardCustomer> listByDocument(final String document) {
        return cardCustomerRepository.findByDocument(document);
    }
}
