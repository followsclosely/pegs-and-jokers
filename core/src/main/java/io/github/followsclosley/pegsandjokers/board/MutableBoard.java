package io.github.followsclosley.pegsandjokers.board;

import io.github.followsclosley.pegsandjokers.Peg;
import io.github.followsclosley.pegsandjokers.Turn;
import io.github.followsclosley.pegsandjokers.turn.*;

public class MutableBoard extends AbstractBoard {

    public MutableBoard(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    public void perform(Turn turn) throws InvalidMoveException {
        if (turn instanceof ComeOut t) {
            performComeOut(t);
        } else if (turn instanceof Translation t) {
            performTranslation(t);
        } else if (turn instanceof Swap t) {
            performSwap(t);
        } else if (turn instanceof Split t) {
            performSplit(t);
        } else if (turn instanceof Burn t) {
            //todo
        } else {
            System.out.println("ERROR: " + turn);
        }

        //todo: ONly increment if good
        currentPlayerIndex = (currentPlayerIndex + 1) % numberOfPlayers;
    }

    private void performSplit(Split turn) throws InvalidMoveException {

        movePiece(turn.getPegIndex(), turn.getDistance());

        int secondaryDistance = switch (turn.getCard().getValue()) {
            case 7 -> 7 - turn.getDistance();
            case 9 -> (turn.getDistance() > 0) ? turn.getDistance() - 9 : 9 + turn.getDistance();
            default -> throw new RuntimeException("You cant split a " + turn.getCard().getValue() + "!");
        };

        movePiece(turn.getSecondaryPegIndex(), secondaryDistance);
    }

    private void performSwap(Swap turn) throws InvalidMoveException {
        int position = turn.getPegIndex() % 18;
        int boardSegmentIndex = turn.getPegIndex() / 18;
        Peg peg = segments[boardSegmentIndex].board[position];

        int secondaryPosition = turn.getSecondaryPegIndex() % 18;
        int secondaryBoardSegmentIndex = turn.getSecondaryPegIndex() / 18;
        Peg secondary = segments[secondaryBoardSegmentIndex].board[secondaryPosition];

        if (peg != null && secondary != null) {
            segments[secondaryBoardSegmentIndex].board[secondaryPosition] = peg;
            segments[boardSegmentIndex].board[position] = secondary;
        } else {
            System.out.println("ERROR ERROR ERROR");
        }
    }

    private Peg performComeOut(ComeOut turn) throws InvalidMoveException {
        for (int i = 0; i < 5; i++) {
            if (segments[currentPlayerIndex].start[i] != null) {
                Peg peg = segments[currentPlayerIndex].start[i];

                int newPosition = turn.getPegIndex() % 18;
                int newBoardSegmentIndex = turn.getPegIndex() / 18;

                Peg stompedOnPeg = segments[newBoardSegmentIndex].board[newPosition];
                if (stompedOnPeg == null) {
                    segments[newBoardSegmentIndex].board[newPosition] = peg;
                    segments[currentPlayerIndex].start[i] = null;
                } else if (stompedOnPeg.getColor() != peg.getColor()) {
                    segments[newBoardSegmentIndex].board[newPosition] = peg;
                    segments[currentPlayerIndex].start[i] = null;
                    //todo: Send peg to home or sweet spot (need team concept)
                } else {
                    throw new InvalidMoveException("Can not land on your own peg.");
                }

                return peg;
            }
        }
        return null;
    }

    private void performTranslation(Translation turn) throws InvalidMoveException {
        int distance = Math.min(turn.getCard().getValue(),10);
        movePiece(turn.getPegIndex(), distance);
    }

    private void movePiece(int pegIndex, int distance) throws InvalidMoveException {

        int position = pegIndex % 18;
        int boardSegmentIndex = pegIndex / 18;
        Peg peg = segments[boardSegmentIndex].board[position];

        //Null out the old location
        segments[boardSegmentIndex].board[position] = null;

        int newPegIndex = (pegIndex + distance) % (18 * numberOfPlayers);
        int newPosition = newPegIndex % 18;
        int newBoardSegmentIndex = newPegIndex / 18;
        //Get the peg at the landing location
        Peg newPeg = segments[newBoardSegmentIndex].board[newPosition];

        //Move the peg to the new location
        segments[newBoardSegmentIndex].board[newPosition] = peg;

        //Handle stomped on peg
        if (newPeg != null) {

        }
    }
}