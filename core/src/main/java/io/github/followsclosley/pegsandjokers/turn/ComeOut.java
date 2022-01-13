package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class ComeOut extends Turn {
    protected int pegIndex;

    public ComeOut(Card card) {
        super(card);
    }

    public ComeOut(Card card, int pegIndex) {
        super(card);
        this.pegIndex = pegIndex;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public static class ComeOutBuilder extends Builder {
        protected int pegIndex = 8;

        public ComeOutBuilder(Builder builder) {
            this.card = builder.card;
        }

        public ComeOutBuilder peg(int pegIndex) {
            this.pegIndex = pegIndex;
            return this;
        }

        public ComeOut build() {
            return new ComeOut(card, pegIndex);
        }
    }
}
