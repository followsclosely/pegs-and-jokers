package io.github.followsclosley.pegsandjokers;

import java.util.List;

public interface Board {
    Peg getPegInPlay(int pegIndex);

    Peg getPegInStart(int playerIndex, int pegIndex);

    Peg getPegInSafe(int playerIndex, int pegIndex);

    List<Peg> getPegs(int playerIndex);
}
