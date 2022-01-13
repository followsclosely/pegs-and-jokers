package io.github.followsclosley.pegsandjokers;

import io.github.followsclosley.pegsandjokers.ai.Dummy;

/**
 * This interface is all you need to create your own AI.
 *
 * @see Dummy
 */
public interface ArtificialIntelligence {

    /**
     * Gets the color that the AI is playing for.
     *
     * @return color of the AI player
     */
    int getColor();

    /**
     * This method is called by the Engine when it is "your" turn to play.
     *
     * @param board The current state of the game.
     * @return The move to perform.
     */
    Turn yourTurn(Board board, Card... cards);

    default String getName() {
        return getClass().getSimpleName();
    }
}