package io.github.followsclosley.pegsandjokers.swing;

import com.google.common.eventbus.EventBus;
import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Deck;
import io.github.followsclosley.pegsandjokers.swing.events.CardSelectedEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class HandPanel extends JPanel {

    private final Dimension defaultDimension;
    private final CardRenderer cardRenderer;
    private final List<Card> cards = new ArrayList<>();
    private final EventBus eventBus;
    private final int CARD_PADDING = 20;
    private final int CARD_OFFSET = 45;
    private Card selected = null;

    public HandPanel(EventBus eventBus, CardRenderer cardRenderer) {
        this.eventBus = eventBus;
        this.cardRenderer = cardRenderer;
        this.defaultDimension = new Dimension(200, 220);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        Deck deck = new Deck();
        for (int i = 0; i < 10; i++) {
            cards.add(deck.pop());
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0, length = cards.size(); i < length; i++) {
            Card card = cards.get(i);
            g.drawImage(cardRenderer.getImage(card.getSuit().ordinal(), card.getValue()), CARD_PADDING + i * CARD_OFFSET, card.equals(selected) ? 20 : 60, this);
        }
    }

    public void mouseClicked(MouseEvent e) {
        int index = (e.getX() - CARD_PADDING) / CARD_OFFSET;
        if (index >= 0 && index < cards.size()) {
            Card selected = cards.get(index);
            this.selected = (selected.equals(this.selected)) ? null : selected;
            eventBus.post(new CardSelectedEvent(this.selected));
            SwingUtilities.invokeLater(this::repaint);
        } else {
            if (selected != null) {
                selected = null;
                eventBus.post(new CardSelectedEvent(null));
                SwingUtilities.invokeLater(this::repaint);
            }
        }
    }
}