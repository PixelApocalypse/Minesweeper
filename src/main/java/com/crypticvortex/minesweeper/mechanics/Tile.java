package com.crypticvortex.minesweeper.mechanics;

import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * Individual tile data.
 */
public class Tile {
    private boolean isMine;
    private boolean isDiscorvered;
    private int index;
    private FlagType flagType; //INVALID if no flag is present.

    public Tile(Tile tile){
        this(tile.getIndex(), tile.getIsMine(), tile.getFlagType());
        isDiscorvered = tile.getIsDiscovered();
    }

    public Tile(int index, boolean isMine) {
        this.index = index;
        this.isMine = isMine;
        this.isDiscorvered = false;
        flagType = FlagType.INVALID;
    }

    public Tile(int index, boolean isMine, FlagType type) { // Optional constructor for flags.
        this(index, isMine);
        this.flagType = type;
    }

    public boolean getIsMine() { return isMine; }

    public boolean getIsDiscovered() { return isDiscorvered; }

    public void setFlagType(FlagType flagType) {
        this.flagType = flagType;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    public int getIndex() { return index; }

    public boolean DiscoverTile(){
        if(flagType == FlagType.INVALID)
            return false;

        isDiscorvered = true;
        return isMine;
    }
}
