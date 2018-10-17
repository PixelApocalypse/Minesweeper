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

        private final Border empty = BorderFactory.createEmptyBorder();

        public TileButton(Tile tile, int index) {
            setPreferredSize(new Dimension(16, 16));
            setMargin(new Insets(0,0, 0, 0));
            setIcon(MenuIcons.DEFAULT);
            setFocusPainted(false);
            setBorder(empty);
            this.tile = tile;
            this.index = index;
        }

        public void showTile() {
            if(!tile.isShown()) {
                if(tile.isMine()) {
                    setIcon(MenuIcons.MINE_PRESSED);
                } else {
                    int mines = field.getNearbyMines(index);
                    switch(mines) {
                        case 1: setIcon(MenuIcons.NUMBER_1); break;
                        case 2: setIcon(MenuIcons.NUMBER_2); break;
                        case 3: setIcon(MenuIcons.NUMBER_3); break;
                        case 4: setIcon(MenuIcons.NUMBER_4); break;
                        case 5: setIcon(MenuIcons.NUMBER_5); break;
                        case 6: setIcon(MenuIcons.NUMBER_6); break;
                        case 7: setIcon(MenuIcons.NUMBER_7); break;
                        case 8: setIcon(MenuIcons.NUMBER_8); break;
                        default:
                            setIcon(MenuIcons.EMPTY);
                            break;
                    }
                    tile.show();
                }
            }
        }

        public void plantFlag() {
            if(!tile.isShown()) {
                if (tile.getFlagType() == FlagType.INVALID) {
                    if(tile.setFlagType(FlagType.RED))
                        setIcon(MenuIcons.FLAG_RED);
                } else if (tile.getFlagType() == FlagType.RED) {
                    if(tile.setFlagType(FlagType.QUESTION))
                        setIcon(MenuIcons.FLAG_QUESTION);
                } else if (tile.getFlagType() == FlagType.QUESTION) {
                    if(tile.setFlagType(FlagType.INVALID))
                        setIcon(MenuIcons.DEFAULT);
                }
            }
        }

        public void cycleColor() {
            if(!tile.isShown()) {
                if (tile.getFlagType() != FlagType.INVALID) {
                    switch(tile.getFlagType()) {
                        case BLUE:
                            if(tile.setFlagType(FlagType.GREEN))
                                setIcon(MenuIcons.FLAG_GREEN);
                            break;
                        case GREEN:
                            if(tile.setFlagType(FlagType.RED))
                                setIcon(MenuIcons.FLAG_RED);
                            break;
                        case RED:
                            if(tile.setFlagType(FlagType.PURPLE))
                                setIcon(MenuIcons.FLAG_PURPLE);
                            break;
                        case PURPLE:
                            if(tile.setFlagType(FlagType.MAROON))
                                setIcon(MenuIcons.FLAG_MAROON);
                            break;
                        case MAROON:
                            if(tile.setFlagType(FlagType.TURQUOISE))
                                setIcon(MenuIcons.FLAG_TURQUOISE);
                            break;
                        case TURQUOISE:
                            if(tile.setFlagType(FlagType.BLACK))
                                setIcon(MenuIcons.FLAG_BLACK);
                            break;
                        case BLACK:
                            if(tile.setFlagType(FlagType.GRAY))
                                setIcon(MenuIcons.FLAG_GRAY);
                            break;
                        case GRAY:
                            if(tile.setFlagType(FlagType.BLUE))
                                setIcon(MenuIcons.FLAG_BLUE);
                            break;
                        default: break;
                    }
                }
            }
        }
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
