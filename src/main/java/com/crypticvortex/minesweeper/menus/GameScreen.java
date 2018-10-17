package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private JButton[] tiles;

    /*public GameScreen(Minefield field) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int size = field.getWidth() * field.getHeight();

        tiles = new JButton[size];

        c.ipadx = 32;
        c.ipady = 32;
        c.fill = GridBagConstraints.BOTH;

        int xp = 0, yp = 0;
        for(int x = 0; x < size; x++) {
            c.gridx = xp;
            c.gridy = yp;

            int index = field.getWidth() * yp + xp;
            JButton tile = new JButton((field.getTile(index).isMine() ? MenuIcons.MINE : MenuIcons.NUMBER_1));
            tile.setSize(32, 32);
            tiles[index] = tile;
            add(tile, c);

            if(x % field.getWidth() == 0 && x != 0) {
                xp = 0; yp += 1;
            }
            xp++;
        }
    }*/

    public GameScreen(Minefield field) {
        setLayout(new GridLayout(field.getWidth(), field.getHeight()));

        int size = field.getWidth() * field.getHeight();

        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                JButton tile = new JButton("" + (x + 1)/*(field.getTile(index).isMine() ? MenuIcons.MINE : MenuIcons.NUMBER_1)*/);
                tile.setSize(50, 50);
                tiles[Math.min(index, size)] = tile;
                add(tile);
            }
        }
    }

}
