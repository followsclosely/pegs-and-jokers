package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class Translation extends Turn {
    protected int pegIndex;

    protected Translation(Card card, int pegIndex) {
        super(card);
        this.pegIndex = pegIndex;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public static class TranslationBuilder extends Builder {
        protected int pegIndex;

        public TranslationBuilder(Builder builder) {
            this.card = builder.card;
        }

        public TranslationBuilder peg(int pegIndex) {
            this.pegIndex = pegIndex;
            return this;
        }

        public Translation build() {
            return new Translation(card, pegIndex);
        }
    }
}