package com.jefersonalmeida.card.application;

import com.jefersonalmeida.card.domain.Card;
import com.jefersonalmeida.card.infrastructure.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card save(final Card card) {
        return cardRepository.save(card);
    }

    public List<Card> findByIncomeLessThanEqual(final Long income) {
        return cardRepository.findByIncomeLessThanEqual(
                BigDecimal.valueOf(Objects.requireNonNullElse(income, 0L))
        );
    }
}
