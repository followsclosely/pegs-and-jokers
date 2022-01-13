package io.github.followsclosley.pegsandjokers.board;

import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Peg;

import java.util.*;
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

    public List<Peg> getPegs(int playerIndex){
        return segments[playerIndex].pegs;
    }

    public int getStartPegIndex(int playerIndex) {
        return playerIndex * 18 + DEFAULT_STARTING_INDEX;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public List<Card> getCards(int playerIndex) {
        return segments[playerIndex].cards;
    }

    protected static class BoardSegment {

        protected final int color;

        protected final List<Peg> pegs;
        protected final List<Card> cards = new ArrayList<>();

        protected final Peg[] board = new Peg[18];
        protected final Peg[] start = new Peg[5];
        protected final Peg[] safe = new Peg[5];


        private BoardSegment(int color) {
            this.color = color;
            for (int i = 0; i < 5; i++) {
                this.start[i] = new Peg(color, i);
            }

            this.pegs = List.of(this.start);
        }
    }
}
