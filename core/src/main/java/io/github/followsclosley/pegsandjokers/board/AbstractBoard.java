package io.github.followsclosley.pegsandjokers.board;

import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Peg;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AbstractBoard implements Board {

    public static final int DEFAULT_STARTING_INDEX = 8;

    protected final int numberOfPlayers;
    protected final BoardSegment[] segments;
    protected int currentPlayerIndex = 0;

    public AbstractBoard(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.segments = IntStream.range(0, numberOfPlayers).mapToObj(BoardSegment::new).toArray(BoardSegment[]::new);
    }

    @Override
    public Peg getPegInPlay(int pegIndex) {
        int position = pegIndex % 18;
        int boardSegmentIndex = pegIndex / 18;
        return segments[boardSegmentIndex].board[position];
    }

    @Override
    public Peg getPegInStart(int playerIndex, int pegIndex) {
        return segments[playerIndex].start[pegIndex];
    }

    @Override
    public Peg getPegInSafe(int playerIndex, int pegIndex) {
        return segments[playerIndex].safe[pegIndex];
    }

    public int getStartPegIndex(int playerIndex) {
        return playerIndex * 18 + DEFAULT_STARTING_INDEX;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public List<Card> getCards(int playerIndex){
        return segments[playerIndex].cards;
    }

    protected static class BoardSegment {
        protected final Peg[] board = new Peg[18];
        protected final Peg[] start = new Peg[5];
        protected final Peg[] safe = new Peg[5];
        protected final List<Card> cards = new ArrayList<>();

        BoardSegment(int color) {
            for (int i = 0; i < 5; i++) {
                start[i] = new Peg(color);
            }
        }
    }
}
