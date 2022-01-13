package io.github.followsclosley.pegsandjokers.swing;

import com.google.common.eventbus.EventBus;
import io.github.followsclosley.pegsandjokers.ArtificialIntelligence;
import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.ai.Dummy;
import io.github.followsclosley.pegsandjokers.board.MutableBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class uses a builder patter to launch a swing UI to
 * test your AI.
 */
public class SwingSupport {

    public static final int PLAYER_COLOR = 1;
    public static final int COMPUTER_COLOR = 2;

    private ArtificialIntelligence bot;
    private Board board;


    public static void main(String[] args) {
        new SwingSupport()
                .setArtificialIntelligence(new Dummy(COMPUTER_COLOR))
                .run();
    }

    /**
     * You can pass in your own board. This allows you to:
     * <ol>
     *     <li>Set up the state of the board</li>
     *     <li>To configure the size of the board</li>
     *     <li>To configure other settings suck as the goal</li>
     * </ol>
     *
     * @param board The board to hold the state of the game
     * @return this to keep the builder going
     */
    public SwingSupport setBoard(Board board) {
        this.board = board;
        return this;
    }

    public SwingSupport setArtificialIntelligence(ArtificialIntelligence bot) {
        this.bot = bot;
        return this;
    }

    /**
     * Launches the JFrame that contains the BoardPanel to display the game.
     */
    public void run() {

        EventBus eventBus = new EventBus("Default Event Bus");

        if (board == null) {
            board = new MutableBoard(4);
        }

        BoardPanel boardPanel = new BoardPanel(eventBus, board);
        eventBus.register(boardPanel);
        boardPanel.setDrawDebugLines(false);
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                boardPanel.mouseClicked(e);
            }
        });

        HandPanel handPanel = new HandPanel(eventBus, new CardRenderer().init());
        eventBus.register(handPanel);
        handPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handPanel.mouseClicked(e);
            }
        });

        JFrame frame = new JFrame("Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(handPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}