package io.github.followsclosley.pegsandjokers.ai;

import io.github.followsclosley.pegsandjokers.ArtificialIntelligence;
import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Turn;

import java.util.Random;

/**
 * A totally random impl of AI.
 */
public class Dummy implements ArtificialIntelligence {

    private final Random random = new Random();

    private final int color;

    public Dummy(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Turn yourTurn(Board board, Card... cards) {
        return null;
    }
}