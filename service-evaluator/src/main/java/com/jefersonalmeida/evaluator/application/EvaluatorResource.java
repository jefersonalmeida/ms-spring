package com.jefersonalmeida.evaluator.application;

import com.jefersonalmeida.evaluator.domain.model.CardSolicitation;
import com.jefersonalmeida.evaluator.domain.model.EvaluatorRequest;
import com.jefersonalmeida.evaluator.exception.CardSolicitationException;
import com.jefersonalmeida.evaluator.exception.ComunicationException;
import com.jefersonalmeida.evaluator.exception.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Objects;

@RestController
@RequestMapping("evaluators")
public class EvaluatorResource {
    private static final Logger log = LoggerFactory.getLogger(EvaluatorResource.class);

    private final EvaluatorService evaluatorService;

    public EvaluatorResource(final EvaluatorService evaluatorService) {
        this.evaluatorService = evaluatorService;
    }

    @GetMapping
    public String status() {

        log.info("Obtendo o status do microserviço service-evaluator: {}", Instant.now());
        return "ok";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> executeEvaluator(@RequestBody final EvaluatorRequest request) {
        try {
            final var response = evaluatorService.executeEvaluator(request);
            return ResponseEntity.ok(response);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ComunicationException e) {
            final var status = Objects.requireNonNullElse(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }

    @PostMapping(
            value = "card-solicitation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> cardSolicitation(@RequestBody final CardSolicitation request) {
        try {
            final var response = evaluatorService.sendCardSolicitation(request);
            return ResponseEntity.ok(response);
        } catch (CardSolicitationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(value = "situation/{document}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSituationCustomer(@PathVariable(name = "document") final String document) {
        try {
            final var situation = evaluatorService.findSituationCustomer(document);
            return ResponseEntity.ok(situation);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ComunicationException e) {
            final var status = Objects.requireNonNullElse(e.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }
}
