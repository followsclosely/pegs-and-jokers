package io.github.followsclosley.pegsandjokers;

public interface Board {
    Peg getPegInPlay(int pegIndex);
    Peg getPegInStart(int playerIndex, int pegIndex);
    Peg getPegInSafe(int playerIndex, int pegIndex);
}
