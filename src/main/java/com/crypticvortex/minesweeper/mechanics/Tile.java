package com.crypticvortex.minesweeper.mechanics;

import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * Individual tile data.
 */
public class Tile {
    private boolean isMine;
    private boolean isDiscorvered;
    private FlagType flagType; //INVALID if no flag is present.

    /**
     * Create a new tile identical to the given one.
     * @param tile Type to copy
     */
    public Tile(Tile tile){
        this(tile.getIsMine(), tile.getFlagType());
        isDiscorvered = tile.getIsDiscovered();
    }

    /**
     * Create a new tile
     * @param isMine if the tile should be considered as a mine
     */
    public Tile(boolean isMine) {
        this.isMine = isMine;
        this.isDiscorvered = false;
        flagType = FlagType.INVALID;
    }

    /**
     * Create a new tile with the given flag.
     * @param isMine if the tile should be considered as a mine
     * @param type what flag should the tile have
     */
    public Tile(boolean isMine, FlagType type) { // Optional constructor for flags.
        this(isMine);
        this.flagType = type;
    }

    public boolean getIsMine() { return isMine; }

    public boolean getIsDiscovered() { return isDiscorvered; }

    public void setFlagType(FlagType flagType) {
        if(!isDiscorvered)
            this.flagType = flagType;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    /**
     * Set the tile as shown if it does not have a flag.
     * @return true if the tile is a mine
     */
    public boolean showTile(){
        if(flagType == FlagType.INVALID)
            return false;
        isDiscorvered = true;
        return isMine;
    }
}
