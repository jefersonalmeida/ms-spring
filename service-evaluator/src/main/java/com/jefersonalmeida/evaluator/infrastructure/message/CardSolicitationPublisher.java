package com.jefersonalmeida.evaluator.infrastructure.message;

import com.jefersonalmeida.evaluator.config.Json;
import com.jefersonalmeida.evaluator.domain.model.CardSolicitation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CardSolicitationPublisher {
    private static final Logger log = LoggerFactory.getLogger(CardSolicitationPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public CardSolicitationPublisher(final RabbitTemplate rabbitTemplate, final Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendCardEmitSolicitation(CardSolicitation data) {
        log.info("Enviando mensagem para emissão de cartão: {}", data);

        final var json = Json.writeValueAsString(data);

        rabbitTemplate.convertAndSend(queue.getName(), json);
    }
}
