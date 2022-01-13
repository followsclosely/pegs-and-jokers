package io.github.followsclosley.pegsandjokers.swing;

import io.github.followsclosley.pegsandjokers.Card;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class CardRenderer {

    private final Image[][] sprites = new Image[4][14];

    public CardRenderer init() {
        try {
            for (Card.Suit s : Card.Suit.values()) {
                for (int n = 0; n <= 13; n++) {
                    sprites[s.ordinal()][n] = ImageIO.read(CardRenderer.class.getResourceAsStream("/cards/" + n + "_of_" + s.name().toLowerCase() + ".png"))
                            .getScaledInstance(500 / 5, 726 / 5, Image.SCALE_SMOOTH);
                }
            }
        } catch (IOException ouch) {
            ouch.printStackTrace();
        }

        return this;
    }

    public Image getImage(int suit, int number) {
        return sprites[suit][number];
    }
}
