package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.menus.MenuIcons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Individual tile data.
 *
 * @author Jatboy
 * @author Caraibe8
 */
public class Tile extends JButton {
    private int id;
    private boolean isMine;
    private boolean isDiscovered;
    private FlagType flagType; //INVALID if no flag is present.

    /**
     * Create a new tile identical to the given one.
     * @param tile Type to copy
     */
    public Tile(Tile tile){
        this(tile.getId(), tile.isMine(), tile.getFlagType());
        isDiscovered = tile.isShown();
    }

    /**
     * Create a new tile
     * @param id of the tile
     * @param isMine if the tile should be considered as a mine
     */
    public Tile(int id, boolean isMine) {
        this.id = id;
        this.isMine = isMine;
        this.isDiscovered = false;
        flagType = FlagType.INVALID;

        setPreferredSize(new Dimension(16, 16));
        setMargin(new Insets(0,0, 0, 0));
        setIcon(MenuIcons.DEFAULT);
        setFocusPainted(false);
        setBorder(empty);
    }

    /**
     * Create a new tile with the given flag.
     * @param id of the tile
     * @param isMine if the tile should be considered as a mine
     * @param type what flag should the tile have
     */
    public Tile(int id, boolean isMine, FlagType type) {
        this(id, isMine);
        this.flagType = type;
    }

    public int getId() { return id; }

    public boolean isMine() {
        return isMine;
    }

    public boolean isShown() {
        return isDiscovered;
    }

    /**
     * Set the tile to have a flag of specified color.
     * @param flagType Color of the flag to place.
     * @return If the value is set.
     */
    public boolean setFlagType(FlagType flagType) {
        if(!isDiscovered) {
            this.flagType = flagType;
            return true;
        }
        return false;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    /**
     * Set the tile as shown if it does not have a flag.
     * @return true if the tile is a mine
     */
    public boolean showTile(){
        if(flagType != FlagType.INVALID)
            return false;
        isDiscovered = true;
        return true;
    }


    private final Border empty = BorderFactory.createEmptyBorder();

    public void cycleColor() {
        if(!isDiscovered) {
            if (flagType != FlagType.INVALID) {
                switch(flagType) {
                    case BLUE:
                        if(setFlagType(FlagType.GREEN))
                            setIcon(MenuIcons.FLAG_GREEN);
                        break;
                    case GREEN:
                        if(setFlagType(FlagType.RED))
                            setIcon(MenuIcons.FLAG_RED);
                        break;
                    case RED:
                        if(setFlagType(FlagType.PURPLE))
                            setIcon(MenuIcons.FLAG_PURPLE);
                        break;
                    case PURPLE:
                        if(setFlagType(FlagType.MAROON))
                            setIcon(MenuIcons.FLAG_MAROON);
                        break;
                    case MAROON:
                        if(setFlagType(FlagType.TURQUOISE))
                            setIcon(MenuIcons.FLAG_TURQUOISE);
                        break;
                    case TURQUOISE:
                        if(setFlagType(FlagType.BLACK))
                            setIcon(MenuIcons.FLAG_BLACK);
                        break;
                    case BLACK:
                        if(setFlagType(FlagType.GRAY))
                            setIcon(MenuIcons.FLAG_GRAY);
                        break;
                    case GRAY:
                        if(setFlagType(FlagType.BLUE))
                            setIcon(MenuIcons.FLAG_BLUE);
                        break;
                    default: break;
                }
            }
        }
    }
}