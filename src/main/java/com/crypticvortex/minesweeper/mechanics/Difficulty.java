package com.crypticvortex.minesweeper.mechanics;

/**
 * Enum of the difficulty settings and their adjustments to mine percentile.
 *
 * @author Jatboy
 * @author Caraibe8
 */
public enum Difficulty {
    EASY(-10.0f),
    MEDIUM(-0.0f),
    HARD(-10.0f),
    EXPERT(-20.0f);

    private float amount;

    Difficulty(float amount) {
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

}
