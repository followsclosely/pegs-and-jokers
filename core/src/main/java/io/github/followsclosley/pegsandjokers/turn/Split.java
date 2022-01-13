package io.github.followsclosley.pegsandjokers.turn;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class Split extends Turn {
    protected int pegIndex;
    protected int distance;
    protected int secondaryPegIndex;

    public Split(Card card, int pegIndex, int distance, int secondaryPegIndex) {
        super(card);
        this.pegIndex = pegIndex;
        this.distance = distance;
        this.secondaryPegIndex = secondaryPegIndex;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public int getSecondaryPegIndex() {
        return secondaryPegIndex;
    }

    public int getDistance() {
        return distance;
    }

    public static class SplitBuilder extends Turn.Builder {
        protected int distance = -1;
        protected int pegIndex = -1;
        protected int secondaryPegIndex = -1;

        public SplitBuilder(Turn.Builder builder) {
            this.card = builder.card;
        }

        public SplitBuilder peg(int pegIndex) {
            if (this.pegIndex == -1) {
                this.pegIndex = pegIndex;
            } else {
                this.secondaryPegIndex = pegIndex;
            }
            return this;
        }

        public SplitBuilder distance(int distance) {
            if (this.distance == -1) {
                this.distance = distance;
            }
            return this;
        }

        public Split build() {
            return new Split(card, pegIndex, distance, secondaryPegIndex);
        }
    }
}
