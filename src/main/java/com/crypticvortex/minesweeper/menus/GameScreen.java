package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setLayout(new MigLayout("", "[16lp, fill]0", "[15lp, fill]0"));
        setBorder(BorderFactory.createLoweredBevelBorder());

        int size = field.getWidth() * field.getHeight();
        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                boolean isMine = field.getTile(index).isMine();
                TileButton tile = new TileButton(field.getTile(index), index);
                tile.addActionListener(new TileListener());
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

        public TileButton(Tile tile, int index) {
            setPreferredSize(new Dimension(16, 16));
            setMargin(new Insets(0,0, 0, 0));
            setIcon(MenuIcons.DEFAULT);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder());
            this.tile = tile;
        }

        public void showTile() {
            if(!tile.isShown()) {
                int mines = field.getNearbyMines(index);
                System.out.println("Nearby mines: " + mines);
                /*switch(mines) {
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
                }*/
                tile.show();
            }
        }

        public Tile getTile() {
            return tile;
        }
    }

    private class TileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TileButton tile = (TileButton) e.getSource();
            tile.showTile();
        }
    }

}
