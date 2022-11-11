package com.jefersonalmeida.evaluator.application;

import com.jefersonalmeida.evaluator.domain.model.*;
import com.jefersonalmeida.evaluator.exception.CardSolicitationException;
import com.jefersonalmeida.evaluator.exception.ComunicationException;
import com.jefersonalmeida.evaluator.exception.CustomerNotFoundException;
import com.jefersonalmeida.evaluator.infrastructure.client.CardResourceClient;
import com.jefersonalmeida.evaluator.infrastructure.client.CustomerResourceClient;
import com.jefersonalmeida.evaluator.infrastructure.message.CardSolicitationPublisher;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
public class EvaluatorService {
    private static final Logger log = LoggerFactory.getLogger(EvaluatorService.class);

    private final CustomerResourceClient customerResourceClient;
    private final CardResourceClient cardResourceClient;
    private final CardSolicitationPublisher cardSolicitationPublisher;

    public EvaluatorService(
            final CustomerResourceClient customerResourceClient,
            final CardResourceClient cardResourceClient,
            final CardSolicitationPublisher cardSolicitationPublisher
    ) {
        this.customerResourceClient = customerResourceClient;
        this.cardResourceClient = cardResourceClient;
        this.cardSolicitationPublisher = cardSolicitationPublisher;
    }

    public CustomerSituation findSituationCustomer(final String document) throws CustomerNotFoundException, ComunicationException {

        try {
            final var customerResponse = customerResourceClient.findByDocument(document);
            final var cardResponse = cardResourceClient.findByDocument(document);

            return CustomerSituation.builder()
                    .customer(customerResponse.getBody())
                    .cards(cardResponse.getBody())
                    .build();

        } catch (FeignException.FeignClientException e) {
            log.error("Erro ao buscar situação do cliente: {} - {}", document, e.getMessage(), e);
            final var status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new CustomerNotFoundException(document);
            }
            throw new ComunicationException(e.getMessage(), status);
        }
    }

    public EvaluatorResponse executeEvaluator(final EvaluatorRequest request) throws CustomerNotFoundException, ComunicationException {
        try {
            final var customerResponse = customerResourceClient.findByDocument(request.document());
            final var cardResponse = cardResourceClient.findByIncomeLessThanEqual(request.income());

            final var cards = cardResponse.getBody();

            final var cardsApproved = Objects.requireNonNull(cards).stream()
                    .map(card -> this.calculateLimit(Objects.requireNonNull(customerResponse.getBody()), card))
                    .toList();

            return new EvaluatorResponse(cardsApproved);

        } catch (FeignException.FeignClientException e) {
            log.error("Erro ao buscar situação do cliente: {} - {}", request.document(), e.getMessage(), e);
            final var status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new CustomerNotFoundException(request.document());
            }
            throw new ComunicationException(e.getMessage(), status);
        }
    }

    public CardProtocolSolicitation sendCardSolicitation(CardSolicitation data) {
        try {
            cardSolicitationPublisher.sendCardEmitSolicitation(data);

            final var protocol = UUID.randomUUID().toString().toUpperCase().replace("-", "");

            return new CardProtocolSolicitation(protocol);

        } catch (Exception e) {
            log.error("Erro ao solicitar cartão: {}", e.getMessage(), e);
            throw new CardSolicitationException(e.getMessage());
        }
    }

    private Card calculateLimit(Customer customer, CardResponse card) {
        final var cardLimit = card.cardLimit();
        final var age = BigDecimal.valueOf(customer.age());

        final var factor = age.divide(BigDecimal.valueOf(10.0));
        final var limit = factor.multiply(cardLimit);

        return new Card(card.name(), card.flag(), limit);
    }
}
