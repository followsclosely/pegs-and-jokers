package io.github.followsclosley.pegsandjokers;

public class Peg {
    private final int color;

    private int pegIndex;
    private boolean isInPlay = false;
    private boolean isSafe = false;
    private boolean isHome = true;

    public Peg(int color, int pegIndex) {
        this.color = color;
        this.pegIndex = pegIndex;
    }

    public int getColor() {
        return color;
    }

    public int getPegIndex() {
        return pegIndex;
    }

    public void setPegIndex(int pegIndex) {
        this.pegIndex = pegIndex;
    }

    public boolean isInPlay() {
        return isInPlay;
    }

    public void setInPlay(boolean inPlay) {
        isInPlay = inPlay;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    @Override
    public String toString() {
        return "Peg{" +
                "color=" + color +
                ", pegIndex=" + pegIndex +
                '}';
    }
}