package com.crypticvortex.minesweeper.mechanics;

/**
 * Individual tile data.
 */
public class Tile {
    private int data; // 0 - Empty, 1 = Mine
    private int coordinate; // x * y

    public Tile(int coordinate, int data) {
        this.coordinate = coordinate;
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public int getCoordinate() {
        return coordinate;
    }
}
