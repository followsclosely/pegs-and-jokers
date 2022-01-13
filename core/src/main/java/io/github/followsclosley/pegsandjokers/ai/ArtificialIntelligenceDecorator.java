package io.github.followsclosley.pegsandjokers.ai;

import io.github.followsclosley.pegsandjokers.ArtificialIntelligence;
import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

public class ArtificialIntelligenceDecorator implements ArtificialIntelligence {

    protected final ArtificialIntelligence parent;

    public ArtificialIntelligenceDecorator(ArtificialIntelligence parent) {
        this.parent = parent;
    }

    @Override
    public int getColor() {
        return this.parent.getColor();
    }

    @Override
    public Turn yourTurn(Board board, Card... cards) {
        return this.parent.yourTurn(board, cards);
    }

    @Override
    public String getName() {
        return this.parent.getName();
    }

    public ArtificialIntelligence getParent() {
        return parent;
    }
}
