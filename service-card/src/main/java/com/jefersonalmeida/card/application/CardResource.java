package com.jefersonalmeida.card.application;

import com.jefersonalmeida.card.application.representation.CardCustomerResponse;
import com.jefersonalmeida.card.application.representation.CardSaveRequest;
import com.jefersonalmeida.card.domain.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("cards")
public class CardResource {

    private static final Logger log = LoggerFactory.getLogger(CardResource.class);

    private final CardService cardService;
    private final CardCustomerService cardCustomerService;

    public CardResource(final CardService cardService, final CardCustomerService cardCustomerService) {
        this.cardService = cardService;
        this.cardCustomerService = cardCustomerService;
    }

    @GetMapping
    public String status() {

        log.info("Obtendo o status do microserviço service-card: {}", Instant.now());
        return "ok";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Card> create(@RequestBody final CardSaveRequest request) {
        final var card = cardService.save(request.toEntity());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(card.getId())
                .toUri();

        log.info("Card created: {} - {}", location, card);

        return ResponseEntity.created(URI.create("/cards/" + card.getId())).body(card);
    }

    @GetMapping(value = "income/{income}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Card>> findByIncomeLessThanEqual(@PathVariable(name = "income") final Long income) {
        final var result = cardService.findByIncomeLessThanEqual(income);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "customer/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CardCustomerResponse>> findByDocument(@PathVariable(name = "document") final String document) {
        final var list = cardCustomerService.listByDocument(document);
        final var result = list.stream().map(CardCustomerResponse::fromModel).toList();
        return ResponseEntity.ok(result);
    }
}
