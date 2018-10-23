package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.menus.MenuIcons;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

/**
 * Individual tile data.
 *
 * @author Jatboy
 * @author Caraibe8
 */
public class Tile extends JButton {
    private int id;
    private GameScale scale;
    private FlagType flagType;
    private boolean isDiscovered, isMine;
    private final Border empty = BorderFactory.createEmptyBorder();

    /**
     * Create a new tile
     * @param id of the tile
     * @param isMine if the tile should be considered as a mine
     */
    public Tile(int id, boolean isMine, GameScale scale) {
        this.id = id;
        this.scale = scale;
        this.isMine = isMine;
        this.isDiscovered = false;
        flagType = FlagType.INVALID;

        setPreferredSize(new Dimension(16, 16));
        setMargin(new Insets(0,0, 0, 0));
        setIcon(MenuIcons.getScaledIcon(scale, MenuIcons.DEFAULT));
        setFocusPainted(false);
        setBorder(empty);
    }

    /**
     * Alternates the color of the flag on this tile.
     */
    public void cycleColor() {
        if(!isDiscovered)
            if (flagType != FlagType.INVALID) {
                int index = Arrays.asList(FlagType.values()).indexOf(flagType) + 1;
                if(index == flagType.values().length)
                    index = 2;
                FlagType next = FlagType.values()[index];
                setFlagType(next);
            }
    }

    public int getId() {
        return id;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isShown() {
        return isDiscovered;
    }

    /**
     * Set the tile as shown if it does not have a flag.
     * @return true if the tile is a mine
     */
    public boolean showTile(){
        if(flagType != FlagType.INVALID || isShown())
            return false;
        isDiscovered = true;
        return true;
    }

    public FlagType getFlagType() {
        return flagType;
    }

    /**
     * Set the tile to have a flag of specified color.
     * @param flagType Color of the flag to place.
     * @return If the value is set.
     */
    public boolean setFlagType(FlagType flagType) {
        if(!isDiscovered) {
            this.flagType = flagType;
            setIcon(MenuIcons.getScaledIcon(scale, flagType.getIconPath()));
            return true;
        }
        return false;
    }

}