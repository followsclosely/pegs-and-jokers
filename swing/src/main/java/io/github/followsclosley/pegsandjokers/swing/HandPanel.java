package io.github.followsclosley.pegsandjokers.swing;

import io.github.followsclosley.pegsandjokers.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HandPanel extends JPanel {

    private final Dimension defaultDimension;
    private final CardRenderer cardRenderer;
    private final List<Card> cards = new ArrayList<>();

    public HandPanel(CardRenderer cardRenderer) {
        this.cardRenderer = cardRenderer;
        this.defaultDimension = new Dimension(200, 200);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < 5; i++) {
            g.drawImage(cardRenderer.getImage(i%4, i), 20 + i*35, 40, this);
        }
    }
}