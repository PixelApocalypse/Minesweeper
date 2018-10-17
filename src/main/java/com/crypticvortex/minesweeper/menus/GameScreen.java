package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.FlagType;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Transform Minefield data into a grid of clickable buttons.
 *
 * @author Jatboy
 */
public class GameScreen extends JPanel {
    private Minefield field;
    private JButton[] tiles;

    public GameScreen(Minefield field) {
        this.field = field;
        setLayout(new MigLayout(new LC().insets("0").align("center", "center").gridGap("0", "0"), new AC().size("16p")));
        setBorder(BorderFactory.createLoweredBevelBorder());

        int size = field.getWidth() * field.getHeight();
        setSize(size + 10, size + 10);
        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                boolean isMine = field.getTile(index).isMine();
                TileButton tile = new TileButton(field.getTile(index), index);
                tile.addActionListener(new TileClickListener());
                tile.addMouseListener(new TileMouseListener());
                tile.setSize(16, 16);
                tiles[Math.min(index, size)] = tile;
                if(x == field.getWidth() - 1)
                    add(tile, "wrap");
                else
                    add(tile);
            }
        }
    }

    private class TileButton extends JButton {
        private Tile tile;
        private int index;

    }

    private class TileClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TileButton tile = (TileButton) e.getSource();
            tile.showTile();
        }
    }

    private class TileMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            TileButton tile = (TileButton) e.getSource();
            if(e.getButton() == MouseEvent.BUTTON3)
                tile.plantFlag();
            if(e.getButton() == MouseEvent.BUTTON2)
                tile.cycleColor();
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }

}
