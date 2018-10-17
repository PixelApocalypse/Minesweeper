package com.crypticvortex.minesweeper.menus;

import javax.swing.*;
import java.awt.*;

/**
 * Main interface upon app start.
 *
 * @author Jatboy
 */
public class StartScreen extends JPanel {

    /**
     * LAYOUT CONCEPT:
     * New Game button next to a dropdown containing various grid sizes such as 32x32 or 16x16.
     * Below that is a preview window of the size of the playing field.
     */

    public StartScreen () {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton newGame = new JButton("New Game");
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1; c.gridheight = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.PAGE_START;
        add(newGame, c);

        JComboBox<String> gridSizes = new JComboBox<>(new String[] { "8 x 8", "16 x 16", "32 x 32", "64 x 64", "128 x 128" });
        c.gridx = 1; c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        add(gridSizes, c);
    }

}