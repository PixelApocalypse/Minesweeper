package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
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

    public GameScreen(Minefield field) {
        this.field = field;
        setLayout(new MigLayout(new LC().insets("0").align("center", "center").gridGap("0", "0"), new AC().size("16p")));
        setBorder(BorderFactory.createLoweredBevelBorder());

        int size = field.getWidth() * field.getHeight();
        setSize(size + 10, size + 10);

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                Tile tile = field.getTile(index);
                tile.addActionListener(new TileClickListener());
                tile.addMouseListener(new TileMouseListener());
                tile.setSize(16, 16);
                if(x == field.getWidth() - 1)
                    add(tile, "wrap");
                else
                    add(tile);
            }
        }
    }

    private class TileClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Tile tile = (Tile) e.getSource();
            field.showTile(tile.getId());
        }
    }

    private class TileMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            Tile tile = (Tile) e.getSource();
            if(e.getButton() == MouseEvent.BUTTON3)
                field.plantFlag(tile.getId());
            if(e.getButton() == MouseEvent.BUTTON2)
                tile.cycleColor();
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }

}
