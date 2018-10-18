package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.menus.DifficultyDialog;

/**
 * Enum of the difficulty settings and their adjustments to mine percentile.
 *
 * @author Jatboy
 * @author Caraibe8
 */
public enum Difficulty {
    BEGINNER     (8, 8, 10),
    INTERMEDIATE (16, 16, 40),
    EXPERT       (30, 30, 99),
    CUSTOM       (0, 0, 0),
    EXPERIMENTAL (0, 0, 0); // TODO : Refer to Application design comment

    private int columns, rows, mines;
    Difficulty(int columns, int rows, int mines) {
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getMines() {
        return mines;
    }
}
