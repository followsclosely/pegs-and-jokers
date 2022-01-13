package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class Swap extends Turn {
    protected int pegIndex;
    protected int secondaryPegIndex;

    protected Swap(Card card, int pegIndex, int secondaryPegIndex) {
        super(card);
        this.pegIndex = pegIndex;
        this.secondaryPegIndex = secondaryPegIndex;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public int getSecondaryPegIndex() {
        return secondaryPegIndex;
    }

    public static class SwapBuilder extends Builder {
        protected int pegIndex = -1;
        protected int secondaryPegIndex = -1;

        public SwapBuilder(Builder builder) {
            this.card = builder.card;
        }

        public SwapBuilder peg(int pegIndex) {
            if (this.pegIndex == -1) {
                this.pegIndex = pegIndex;
            } else {
                this.secondaryPegIndex = pegIndex;
            }
            return this;
        }

        public Swap build() {
            return new Swap(card, pegIndex, secondaryPegIndex);
        }
    }
}