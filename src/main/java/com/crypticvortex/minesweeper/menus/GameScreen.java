package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.FlagType;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;
import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Transform Minefield data into a grid of clickable buttons.
 *
 * @author Jatboy
 */
public class GameScreen extends JPanel {
    private Minefield field;
    private CounterPanel counter;
    private boolean isGameFinished;

    public GameScreen(Minefield field, CounterPanel counter) {
        this.field = field;
        this.counter = counter;
        this.isGameFinished = false;
        setLayout(new MigLayout(new LC().insets("0").align("center", "center").gridGap("0", "0"), new AC().size("16p")));
        setBorder(BorderFactory.createLoweredBevelBorder());

        int size = field.getWidth() * field.getHeight();
        setSize(size + 10, size + 10);

        int tileWidth = 16, tileHeight = 16;
        switch (field.getScale()) {
            case TIMES_2:
                tileWidth *= 2;
                tileHeight *= 2;
                break;
        }
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                Tile tile = field.getTile(index);
                tile.addActionListener(new TileClickListener());
                field.createTileMouseListener(index);
                tile.setSize(tileWidth, tileHeight);
                if (x == field.getWidth() - 1)
                    add(tile, "wrap");
                else
                    add(tile);
            }
        }
    }

    private class TileClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!counter.isTimerCounting() && !isGameFinished)
                counter.startTimer();
            Tile tile = (Tile) e.getSource();
            boolean minePressed = field.showTile(tile.getId(), true);
            counter.updateFace(0);
            if (!isGameFinished) {
                if (!minePressed) {
                    if (field.gameWon()) {
                        counter.stopTimer();
                        for (int i : field.getMineCoordinates())
                            if (!field.getTile(i).equals(tile))
                                field.showTile(i, false);
                        isGameFinished = true;
                        JOptionPane.showMessageDialog(Application.get, "Game Won!");
                    }
                } else if (minePressed && tile.getFlagType() == FlagType.INVALID) {
                    counter.stopTimer();
                    for (int i : field.getMineCoordinates())
                        if (!field.getTile(i).equals(tile))
                            field.showTile(i, false);
                    isGameFinished = true;
                    JOptionPane.showMessageDialog(Application.get, "Game Over! Click the face to restart.");
                }
            }
        }
    }

}
