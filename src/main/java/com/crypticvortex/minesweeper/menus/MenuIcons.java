package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;

import javax.swing.*;

public class MenuIcons {
    public static final ImageIcon EMPTY = getIcon(-1);
    public static final ImageIcon MINE = getIcon(0);
    public static final ImageIcon NUMBER_1 = getIcon(1);
    public static final ImageIcon NUMBER_2 = getIcon(2);
    public static final ImageIcon NUMBER_3 = getIcon(3);
    public static final ImageIcon NUMBER_4 = getIcon(4);
    public static final ImageIcon NUMBER_5 = getIcon(5);
    public static final ImageIcon NUMBER_6 = getIcon(6);
    public static final ImageIcon NUMBER_7 = getIcon(7);
    public static final ImageIcon NUMBER_8 = getIcon(8);

    private static ImageIcon getIcon(int file) {
        ImageIcon icon = null;
        switch(file) {
            case -1: // EMPTY
                icon = new ImageIcon(Application.class.getResource("/images/empty.png"));
                break;
            case 0: // Mine
                icon = new ImageIcon(Application.class.getResource("/images/mine.png"));
                break;
            case 1: // Number #1
                icon = new ImageIcon(Application.class.getResource("/images/1.png"));
                break;
            case 2: // Number #2
                icon = new ImageIcon(Application.class.getResource("/images/2.png"));
                break;
            case 3: // Number #3
                icon = new ImageIcon(Application.class.getResource("/images/3.png"));
                break;
            case 4: // Number #4
                icon = new ImageIcon(Application.class.getResource("/images/4.png"));
                break;
            case 5: // Number #5
                icon = new ImageIcon(Application.class.getResource("/images/5.png"));
                break;
            case 6: // Number #6
                icon = new ImageIcon(Application.class.getResource("/images/6.png"));
                break;
            case 7: // Number #7
                icon = new ImageIcon(Application.class.getResource("/images/7.png"));
                break;
            case 8: // Number #8
                icon = new ImageIcon(Application.class.getResource("/images/8.png"));
                break;
        }
        return icon;
    }
}
