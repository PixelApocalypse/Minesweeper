package com.crypticvortex.minesweeper.mechanics;

import org.omg.CORBA.DynAnyPackage.Invalid;

import javax.swing.*;

/**
 * Individual tile data.
 */
public class Tile extends JButton {
    private boolean isMine;
    private boolean isDiscovered;
    private int index;
    private FlagType flagType; //INVALID if no flag is present.

    public Tile(Tile tile){
        this(tile.getIndex(), tile.isMine(), tile.getFlagType());
        isDiscovered = tile.isDiscovered();
    }

    public Tile(int index, boolean isMine) {
        this.index = index;
        this.isMine = isMine;
        this.isDiscovered = false;
        flagType = FlagType.INVALID;
    }

    public Tile(int index, boolean isMine, FlagType type) {
        this(index, isMine);
        this.flagType = type;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setFlagType(FlagType flagType) {
        this.flagType = flagType;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    public int getIndex() {
        return index;
    }

    public boolean discoverTile(){
        if(flagType == FlagType.INVALID)
            return false;

        isDiscovered = true;
        return isMine;
    }

}
