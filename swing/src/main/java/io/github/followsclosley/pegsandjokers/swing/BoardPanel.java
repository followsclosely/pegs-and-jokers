package io.github.followsclosley.pegsandjokers.swing;


import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Peg;

import javax.swing.*;
import java.awt.*;

/**
 * This panel draws the connect four board given a Board
 *
 * @see Board
 */
public class BoardPanel extends JPanel {

    public static final int PADDING = 10;
    public static final int HALF_PADDING = PADDING / 2;
    public static final int PIECE_SIZE = 20;
    public static final int PIECE_WITH_PADDING_SIZE = PIECE_SIZE + PADDING;
    private final Board board;
    protected Dimension defaultDimension;
    private boolean drawDebugLines = false;

    private final Color[] COLORS = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE};

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(19 * PIECE_WITH_PADDING_SIZE + (PADDING), 19 * PIECE_WITH_PADDING_SIZE + (PADDING));
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawSegment(gg, 0, HALF_PADDING, HALF_PADDING, 1, 0, 0, 1);
        drawSegment(gg, 1, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), HALF_PADDING, 0, 1, -1, 0);
        drawSegment(gg, 2, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), -1, 0, 0, -1);
        drawSegment(gg, 3, HALF_PADDING, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), 0, -1, 1, 0);
    }

    private void drawSegment(Graphics2D gg, int playerIndex, int startX, int startY, int deltaX, int deltaY, int safeDeltaX, int safeDeltaY) {

        //Draw the spaces on the board
        for (int h = 0, width = 18; h < width; h++) {
            Peg peg = board.getPegInPlay(18 * playerIndex + h);
            paintSpace(gg, peg, playerIndex, Color.GRAY,
                    startX + h * deltaX * PIECE_WITH_PADDING_SIZE,
                    startY + h * deltaY * PIECE_WITH_PADDING_SIZE, h);
        }

        for (int v = 1; v <= 5; v++) {
            //Draw the safe spot
            paintSpace(gg, board.getPegInSafe(playerIndex, v - 1), playerIndex, COLORS[playerIndex],
                    startX + (v * safeDeltaX * PIECE_WITH_PADDING_SIZE) + (3 * deltaX * PIECE_WITH_PADDING_SIZE),
                    startY + (v * safeDeltaY * PIECE_WITH_PADDING_SIZE) + (3 * deltaY * PIECE_WITH_PADDING_SIZE), v);

            //Draw the start
            paintSpace(gg, board.getPegInStart(playerIndex, v - 1), playerIndex, Color.GRAY,
                    startX + (v * safeDeltaX * PIECE_WITH_PADDING_SIZE) + (8 * deltaX * PIECE_WITH_PADDING_SIZE),
                    startY + (v * safeDeltaY * PIECE_WITH_PADDING_SIZE) + (8 * deltaY * PIECE_WITH_PADDING_SIZE), v);
        }
    }

    private void paintSpace(Graphics2D gg, Peg peg, int playerIndex, Color color, int x, int y, int index) {

        if (peg == null) {
            gg.setColor(color);
            gg.drawOval(x + HALF_PADDING, y + HALF_PADDING, PIECE_SIZE, PIECE_SIZE);
        } else {
            gg.setColor(COLORS[peg.getColor()]);
            gg.fillOval(x + HALF_PADDING, y + HALF_PADDING, PIECE_SIZE, PIECE_SIZE);
        }

        gg.setColor(Color.GRAY);
        gg.drawRect(x, y, PIECE_WITH_PADDING_SIZE, PIECE_WITH_PADDING_SIZE);

        if (drawDebugLines) {
            gg.setColor(Color.BLACK);
            gg.drawString(String.valueOf(index), x + PADDING, y + PIECE_SIZE);
        }
    }

    public void setDrawDebugLines(boolean drawDebugLines) {
        this.drawDebugLines = drawDebugLines;
    }
}