package com.crypticvortex.minesweeper.mechanics;

/**
 * Stores data on mine positions, etc.
 */
public class Minefield {
    private Tile[] tiles;
    private Difficulty diff;
    private int[] fieldData;
    private int width, height;

    private final float MINE_PERCENT = 0.2f;

    public Minefield(int size, Difficulty diff) {
        this.width = size;
        this.height = size;
        this.diff = diff;
        this.tiles = new Tile[size * size];
        this.fieldData = new int[size * size];
    }

    public void populate() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Difficulty getDifficulty() {
        return diff;
    }

    public int[] getFieldData() {
        return fieldData;
    }
}
