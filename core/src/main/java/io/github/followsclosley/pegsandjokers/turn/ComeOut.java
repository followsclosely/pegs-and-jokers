package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class ComeOut extends Turn {
    protected final int pegIndex;
    protected final int startPegIndex;

    public ComeOut(Card card, int startPegIndex, int pegIndex) {
        super(card);
        this.pegIndex = pegIndex;
        this.startPegIndex = startPegIndex;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public int getStartPegIndex() {
        return startPegIndex;
    }

    public static class ComeOutBuilder extends Builder {
        protected int startPegIndex = -1;
        protected int pegIndex = -1;

        public ComeOutBuilder(Builder builder) {
            this.card = builder.card;
        }

        public ComeOutBuilder startPeg(int startPegIndex) {
            this.startPegIndex = startPegIndex;
            return this;
        }

        public ComeOutBuilder peg(int pegIndex) {
            this.pegIndex = pegIndex;
            return this;
        }

        public ComeOut build() {
            return new ComeOut(card, startPegIndex, pegIndex);
        }
    }
}
