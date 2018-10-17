package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;

import javax.swing.*;

/**
 * Image icons for the buttons on the interface.
 *
 * @author jatboy
 */
public class MenuIcons {
    // ----- Un-clicked Icons ----
    public static final ImageIcon DEFAULT  = getIcon("default.png");
    public static final ImageIcon FLAG_RED = getIcon("default_flag.png");
    // ----- Clicked Icons ----
    public static final ImageIcon EMPTY    = getIcon("empty.png");
    public static final ImageIcon MINE     = getIcon("mine.png");
    public static final ImageIcon NUMBER_1 = getIcon("1.png");
    public static final ImageIcon NUMBER_2 = getIcon("2.png");
    public static final ImageIcon NUMBER_3 = getIcon("3.png");
    public static final ImageIcon NUMBER_4 = getIcon("4.png");
    public static final ImageIcon NUMBER_5 = getIcon("5.png");
    public static final ImageIcon NUMBER_6 = getIcon("6.png");
    public static final ImageIcon NUMBER_7 = getIcon("7.png");
    public static final ImageIcon NUMBER_8 = getIcon("8.png");

    /**
     * Retrieves an icon from the local classpath.
     * @param file Index of the file to retrieve.
     * @return Requested image
     */
    private static ImageIcon getIcon(String file) {
        return new ImageIcon(Application.class.getResource("/images/" + file));
    }
}
