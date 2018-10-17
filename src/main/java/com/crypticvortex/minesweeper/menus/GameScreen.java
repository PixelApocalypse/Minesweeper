package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.Minefield;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private JButton[] tiles;

    public GameScreen(Minefield field) {
        setLayout(new GridLayout(field.getWidth(), field.getHeight(), 0, 0 ));

        int size = field.getWidth() * field.getHeight();

        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                JButton tile = new JButton(/*"" + (x + 1)*/(field.getTile(index).isMine() ? MenuIcons.MINE : MenuIcons.NUMBER_1));
                tile.setSize(50, 50);
                tiles[Math.min(index, size)] = tile;
                add(tile);
            }
        }
    }

}
