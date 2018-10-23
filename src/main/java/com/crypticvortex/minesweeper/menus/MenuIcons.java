package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.GameScale;

import javax.swing.*;

/**
 * Image icons for the buttons on the interface.
 *
 * @author jatboy
 */
public class MenuIcons {
    // ----- Counter Icons -----
    // Digits
    public static final String COUNTER_0     = "counter/0.png";
    public static final String COUNTER_1     = "counter/1.png";
    public static final String COUNTER_2     = "counter/2.png";
    public static final String COUNTER_3     = "counter/3.png";
    public static final String COUNTER_4     = "counter/4.png";
    public static final String COUNTER_5     = "counter/5.png";
    public static final String COUNTER_6     = "counter/6.png";
    public static final String COUNTER_7     = "counter/7.png";
    public static final String COUNTER_8     = "counter/8.png";
    public static final String COUNTER_9     = "counter/9.png";
    public static final String COUNTER_DASH  = "counter/-.png";
    // Faces
    public static final String FACE_SMILEY       = "faces/smiley.png";
    public static final String FACE_SMILEY_PRESS = "faces/smiley_press.png";
    public static final String FACE_COOL         = "faces/cool.png";
    public static final String FACE_DEAD         = "faces/dead.png";
    public static final String FACE_NERVOUS      = "faces/nervous.png";
    // ----- Minefield Icons ----
    // Flags
    public static final String FLAG_RED       = "flags/flag_red.png";
    public static final String FLAG_BLUE      = "flags/flag_blue.png";
    public static final String FLAG_GREEN     = "flags/flag_green.png";
    public static final String FLAG_PURPLE    = "flags/flag_purple.png";
    public static final String FLAG_MAROON    = "flags/flag_maroon.png";
    public static final String FLAG_TURQUOISE = "flags/flag_turquoise.png";
    public static final String FLAG_BLACK     = "flags/flag_black.png";
    public static final String FLAG_GRAY      = "flags/flag_gray.png";
    public static final String FLAG_QUESTION  = "flags/flag_question.png";
    // Squares
    public static final String DEFAULT       = "default.png";
    public static final String EMPTY         = "empty.png";
    public static final String MINE          = "mine.png";
    public static final String MINE_PRESSED  = "mine_clicked.png";
    public static final String NUMBER_1      = "1.png";
    public static final String NUMBER_2      = "2.png";
    public static final String NUMBER_3      = "3.png";
    public static final String NUMBER_4      = "4.png";
    public static final String NUMBER_5      = "5.png";
    public static final String NUMBER_6      = "6.png";
    public static final String NUMBER_7      = "7.png";
    public static final String NUMBER_8      = "8.png";

    public static ImageIcon getIconFrom(GameScale scale, int number) {
        switch(number) {
            case 1: return getScaledIcon(scale, NUMBER_1);
            case 2: return getScaledIcon(scale, NUMBER_2);
            case 3: return getScaledIcon(scale, NUMBER_3);
            case 4: return getScaledIcon(scale, NUMBER_4);
            case 5: return getScaledIcon(scale, NUMBER_5);
            case 6: return getScaledIcon(scale, NUMBER_6);
            case 7: return getScaledIcon(scale, NUMBER_7);
            case 8: return getScaledIcon(scale, NUMBER_8);
            default: return getScaledIcon(scale, EMPTY);
        }
    }

    /**
     * Retrieves an icon from the local classpath.
     * @param file Location of the file to retrieve.
     * @return Requested image
     */
    public static ImageIcon getIcon(String file) {
        return new ImageIcon(Application.class.getResource("/images/" + file));
    }

    /**
     * Retrieves an icon from the local classpath.
     * @param scale Which scaled version to retrieve.
     * @param file Location of the file to retrieve.
     * @return Requested image
     */
    public static ImageIcon getScaledIcon(GameScale scale, String file) {
        return new ImageIcon(Application.class.getResource("/images/" + scale.getScale() + file));
    }
}
