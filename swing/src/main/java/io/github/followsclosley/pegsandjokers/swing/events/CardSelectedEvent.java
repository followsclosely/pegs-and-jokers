package io.github.followsclosley.pegsandjokers.swing.events;

import io.github.followsclosley.pegsandjokers.Card;

public record CardSelectedEvent(Card card) {
    public Card getCard() {
        return card;
    }
}