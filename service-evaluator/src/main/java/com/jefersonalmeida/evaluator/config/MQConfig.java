package com.jefersonalmeida.evaluator.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.cards-emit}")
    private String queueCardsEmit;

    @Bean
    public Queue queueCardEmit() {
        return new Queue(queueCardsEmit, true);
    }
}
