package io.github.followsclosley.pegsandjokers;

import io.github.followsclosley.pegsandjokers.turn.*;

public class Turn {
    private final Card card;

    protected Turn(Card card) {
        this.card = card;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Card getCard() {
        return card;
    }

    public static class Builder {
        public Card card;

        public Builder card(Card card) {
            this.card = card;
            return this;
        }

        public ComeOut.ComeOutBuilder comeOut() {
            return new ComeOut.ComeOutBuilder(this);
        }

        public Translation.TranslationBuilder translation() {
            return new Translation.TranslationBuilder(this);
        }

        public Burn.BurnBuilder burn() {
            return new Burn.BurnBuilder(this);
        }

        public Swap.SwapBuilder swap() {
            return new Swap.SwapBuilder(this);
        }

        public Split.SplitBuilder split() {
            return new Split.SplitBuilder(this);
        }
    }
}