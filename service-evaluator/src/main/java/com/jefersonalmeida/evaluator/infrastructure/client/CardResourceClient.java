package com.jefersonalmeida.evaluator.infrastructure.client;

import com.jefersonalmeida.evaluator.domain.model.Card;
import com.jefersonalmeida.evaluator.domain.model.CardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-card", path = "cards")
public interface CardResourceClient {

    @GetMapping(value = "customer/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Card>> findByDocument(@PathVariable(name = "document") final String document);

    @GetMapping(value = "income/{income}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CardResponse>> findByIncomeLessThanEqual(@PathVariable(name = "income") final Long income);
}
