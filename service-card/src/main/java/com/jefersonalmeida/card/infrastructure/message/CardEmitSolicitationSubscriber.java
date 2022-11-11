package com.jefersonalmeida.card.infrastructure.message;

import com.jefersonalmeida.card.application.representation.CardSolicitation;
import com.jefersonalmeida.card.config.Json;
import com.jefersonalmeida.card.domain.CardCustomer;
import com.jefersonalmeida.card.infrastructure.repository.CardCustomerRepository;
import com.jefersonalmeida.card.infrastructure.repository.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CardEmitSolicitationSubscriber {
    private static final Logger log = LoggerFactory.getLogger(CardEmitSolicitationSubscriber.class);

    private final CardRepository cardRepository;
    private final CardCustomerRepository cardCustomerRepository;

    public CardEmitSolicitationSubscriber(
            final CardRepository cardRepository,
            final CardCustomerRepository cardCustomerRepository
    ) {
        this.cardRepository = cardRepository;
        this.cardCustomerRepository = cardCustomerRepository;
    }

    @RabbitListener(queues = "${mq.queues.cards-emit}")
    public void receiveCardEmitSolicitation(@Payload final String payload) {
        log.info("Recebendo mensagem para emissão de cartão: {}", payload);

        final var object = Json.readValue(payload, CardSolicitation.class);

        log.info("Objeto deserializado: {}", object);

        final var card = cardRepository.findById(object.id()).orElseThrow();

        final var cardCustomer = new CardCustomer(object.document(), card, object.limit());
        cardCustomerRepository.save(cardCustomer);
    }
}
