package com.crypticvortex.minesweeper.menus;

import javax.swing.*;
import java.awt.*;

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
        c.gridx = 0;
        c.gridy = 0;
        add(newGame, c);


    }

}
