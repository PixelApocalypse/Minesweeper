package com.crypticvortex.minesweeper.mechanics;

/**
 * Enum of the difficulty settings and their adjustments to mine percentile.
 *
 * @author Jatboy
 * @author Caraibe8
 */
public enum Difficulty {
    BEGINNER     (-10.0f),
    INTERMEDIATE (-0.0f),
    EXPERT       (-10.0f),
    CUSTOM       (0);

    private float amount;

    Difficulty(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
