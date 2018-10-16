package com.crypticvortex.minesweeper.mechanics;

/**
 * Individual tile data.
 */
public class Tile {
    private int data; // 0 = Empty, 1 = Mine, 2 = Flag
    private int coordinate; // x * y
    private FlagType flagType; //INVALID if no flag is present.

    public Tile(int coordinate, int data) {
        this.coordinate = coordinate;
        this.data = data;
    }

    public Tile(int coordinate, int data, FlagType type) { // Optional constructor for flags.
        this(coordinate, data);
        this.flagType = type;
    }

    public int getData() {
        return data;
    }

    public void setFlagType(FlagType flagType) {
        this.flagType = flagType;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    public int getCoordinate() {
        return coordinate;
    }
}
