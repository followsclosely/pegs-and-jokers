package io.github.followsclosley.pegsandjokers.swing;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import io.github.followsclosley.pegsandjokers.Board;
import io.github.followsclosley.pegsandjokers.Peg;
import io.github.followsclosley.pegsandjokers.swing.events.CardSelectedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * @see Board
 */
public class BoardPanel extends JPanel {

    public static final int PADDING = 10;
    public static final int HALF_PADDING = PADDING / 2;
    public static final int PIECE_SIZE = 20;
    public static final int PIECE_WITH_PADDING_SIZE = PIECE_SIZE + PADDING;
    private final Board board;
    private final Color[] COLORS = new Color[]{Color.BLUE, Color.RED, Color.GREEN.darker(), Color.ORANGE};
    private final EventBus eventBus;
    protected Dimension defaultDimension;
    private boolean drawDebugLines = false;
    private final LayoutDetails[] layoutDetails;

    private Peg selected;
    private final Set<Peg> pegsThanCanMoveThisTurn = new HashSet<>();

    public BoardPanel(EventBus eventBus, Board board) {
        this.board = board;
        this.eventBus = eventBus;
        this.defaultDimension = new Dimension(19 * PIECE_WITH_PADDING_SIZE + (PADDING), 19 * PIECE_WITH_PADDING_SIZE + (PADDING));
        this.layoutDetails = new LayoutDetails[] {
                new LayoutDetails(0, HALF_PADDING, HALF_PADDING, 1, 0, 0, 1),
                new LayoutDetails(1, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), HALF_PADDING, 0, 1, -1, 0),
                new LayoutDetails(2, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), -1, 0, 0, -1),
                new LayoutDetails(3, HALF_PADDING, HALF_PADDING + (18 * PIECE_WITH_PADDING_SIZE), 0, -1, 1, 0)
        };
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (LayoutDetails l : layoutDetails) {
            drawSegment(gg, l.playerIndex, l.startX, l.startY, l.deltaX, l.deltaY, l.safeDeltaX, l.safeDeltaY);
        }
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

        if ( (selected != null && selected.equals(peg)) || pegsThanCanMoveThisTurn.contains(peg)){
            gg.setColor(color.darker());
            gg.fillOval(x + HALF_PADDING-2, y + HALF_PADDING-2, PIECE_SIZE+4, PIECE_SIZE+4);
        }

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
            gg.setColor(Color.RED);
            gg.drawString(String.valueOf(index), x + PADDING, y + PIECE_SIZE);

            Rectangle home = layoutDetails[playerIndex].homeRectangle;
            gg.drawRect(home.x, home.y, home.width, home.height);

            Rectangle safe = layoutDetails[playerIndex].safeRectangle;
            gg.drawRect(safe.x, safe.y, safe.width, safe.height);
        }
    }

    @Subscribe
    public void onCardSelected(CardSelectedEvent e) {
        System.out.println(e);

        selected = null;
        pegsThanCanMoveThisTurn.clear();

        if( e.getCard() != null ) {
            int value = e.getCard().getValue();
            for (Peg peg : board.getPegs(2)) {
                System.out.println("  " + peg);

                if (value == 0 || value == 1 || value >= 11) {
                    if (peg.isHome()) {
                        pegsThanCanMoveThisTurn.add(peg);
                    }
                }
            }
        }

        SwingUtilities.invokeLater(this::repaint);
    }

    public void setDrawDebugLines(boolean drawDebugLines) {
        this.drawDebugLines = drawDebugLines;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e);
        for (LayoutDetails details : layoutDetails ) {
            if (details.homeRectangle.contains(e.getX(), e.getY() ) ){
                int dx = (e.getX() - (int)details.homeRectangle.getX()) / PIECE_WITH_PADDING_SIZE;
                int dy = (e.getY() - (int)details.homeRectangle.getY()) / PIECE_WITH_PADDING_SIZE;
                int index = Math.min(details.safeDeltaX, details.safeDeltaY) == -1 ? 4 - Math.max(dx, dy) : Math.max(dx, dy);
                System.out.println("CLICK IN HOME!!!!! " + details.playerIndex + "  (" + dx + "," + dy + ") " + index);

                Peg peg = board.getPegInStart(details.playerIndex, index);
                if( peg != null && pegsThanCanMoveThisTurn.contains(peg)){
                    selected = peg;
                    pegsThanCanMoveThisTurn.clear();
                    SwingUtilities.invokeLater(this::repaint);
                }

            } else if (details.safeRectangle.contains(e.getX(), e.getY() ) ){
                int dx = (e.getX() - (int)details.safeRectangle.getX()) / PIECE_WITH_PADDING_SIZE;
                int dy = (e.getY() - (int)details.safeRectangle.getY()) / PIECE_WITH_PADDING_SIZE;
                int index = Math.min(details.safeDeltaX, details.safeDeltaY) == -1 ? 4 - Math.max(dx, dy) : Math.max(dx, dy);
                System.out.println("CLICK IN SAFE!!!!! " + details.playerIndex + "  (" + dx + "," + dy + ") " + index);

            }
        }
    }

    private static class LayoutDetails {

        public final int playerIndex, startX, startY, deltaX, deltaY, safeDeltaX, safeDeltaY;

        public final Rectangle homeRectangle;
        public final Rectangle safeRectangle;
        //public final Rectangle playRectangle;

        public LayoutDetails(int playerIndex, int startX, int startY, int deltaX, int deltaY, int safeDeltaX, int safeDeltaY) {
            this.playerIndex = playerIndex;
            this.startX = startX;
            this.startY = startY;
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.safeDeltaX = safeDeltaX;
            this.safeDeltaY = safeDeltaY;

            //Calculate the Rectangle the surrounds home.
            this.homeRectangle = calculateRectangle(8);

            this.safeRectangle = calculateRectangle(3);
        }

        private Rectangle calculateRectangle(int offset){

            int rx1 = startX + (safeDeltaX * PIECE_WITH_PADDING_SIZE) + (offset * deltaX * PIECE_WITH_PADDING_SIZE);
            int ry1 = startY + (safeDeltaY * PIECE_WITH_PADDING_SIZE) + (offset * deltaY * PIECE_WITH_PADDING_SIZE);

            int rx2 = startX + (5 * safeDeltaX * PIECE_WITH_PADDING_SIZE) + (offset * deltaX * PIECE_WITH_PADDING_SIZE);
            int ry2 = startY + (5 * safeDeltaY * PIECE_WITH_PADDING_SIZE) + (offset * deltaY * PIECE_WITH_PADDING_SIZE);

            int topLeftx = Math.min(rx1, rx2);
            int topLefty = Math.min(ry1, ry2);

            int bottomRightx = Math.max(rx1, rx2);
            int bottomRighty = Math.max(ry1, ry2);

            int xSize = bottomRightx - topLeftx + PIECE_WITH_PADDING_SIZE;
            int ySize = bottomRighty - topLefty + PIECE_WITH_PADDING_SIZE;

            return new Rectangle(topLeftx, topLefty, xSize,ySize);
        }
    }
}