package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JPanel {
    private JButton[] tiles;

    public GameScreen(Minefield field) {
        setLayout(new GridLayout(field.getWidth(), field.getHeight(), 0, 0 ));

        int size = field.getWidth() * field.getHeight();

        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                boolean isMine = field.getTile(index).isMine();
                TileButton tile = new TileButton(field.getTile(index), MenuIcons.EMPTY);
                if(isMine) tile.setBackground(Color.RED);
                tile.addActionListener(new TileListener());
                tile.setSize(50, 50);
                tiles[Math.min(index, size)] = tile;
                add(tile);
            }
        }
    }

    private class TileButton extends JButton {
        private Tile tile;

        public TileButton(Tile tile, ImageIcon icon) {
            super(icon);
            this.tile = tile;
        }

        public Tile getTile() {
            return tile;
        }
    }

    private class TileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TileButton tile = (TileButton) e.getSource();
            boolean isMine = tile.getTile().isMine();
            if(isMine) {
                tile.setBackground(Color.RED);
                tile.setIcon(MenuIcons.MINE);
            }
        }
    }

}