package io.github.followsclosley.pegsandjokers;

import java.util.Objects;

public class Card {
    private final Suit suit;
    private final int value;
    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES;
        private static final Suit[] values = values();

        public static Suit get(int ordinal) {
            return values[ordinal];
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}