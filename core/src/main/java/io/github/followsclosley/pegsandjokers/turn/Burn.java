package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class Burn extends Turn {
    public Burn(Card card) {
        super(card);
    }

    public static class BurnBuilder extends Builder {
        public BurnBuilder(Builder builder) {
            this.card = builder.card;
        }

        public Burn build() {
            return new Burn(card);
        }
    }
}
