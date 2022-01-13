package io.github.followsclosley.pegsandjokers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {

    private final Stack<Card> cards = new Stack<>();

    private final List<Card> returnPile = new ArrayList<>();

    public Deck() {
        for (int s = 0; s < 4; s++) {
            for (int n = 0; n < 14; n++) {
                this.returnPile.add(new Card(Card.Suit.get(s), n));
            }
        }
    }

    public Card pop() {
        synchronized (this) {
            if (cards.empty()) {
                Collections.shuffle(returnPile);
                cards.addAll(returnPile);
            }
            return cards.pop();
        }
    }
}