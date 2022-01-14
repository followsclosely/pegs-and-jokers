package io.github.followsclosley.pegsandjokers.swing.events;

import io.github.followsclosley.pegsandjokers.Card;
import io.github.followsclosley.pegsandjokers.Peg;

public record PegSelectedEvent(Peg peg) {
    public Peg getPeg(){
        return peg;
    }
}