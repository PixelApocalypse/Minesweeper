package com.crypticvortex.minesweeper.mechanics;

import static com.crypticvortex.minesweeper.menus.MenuIcons.*;

/**
 * All the various types/colors of flags that can be present on a tile.
 *
 * @author Jatboy
 */
public enum FlagType {
    INVALID(DEFAULT),
    QUESTION(FLAG_QUESTION),
    RED(FLAG_RED),
    BLUE(FLAG_BLUE),
    GREEN(FLAG_GREEN),
    PURPLE(FLAG_PURPLE),
    MAROON(FLAG_MAROON),
    TURQUOISE(FLAG_TURQUOISE),
    BLACK(FLAG_BLACK),
    GRAY(FLAG_GRAY);

    private String iconPath;

    FlagType(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }
}
