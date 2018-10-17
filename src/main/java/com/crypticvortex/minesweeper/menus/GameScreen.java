package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.mechanics.Tile;
import javafx.scene.layout.Border;
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
    private JButton[] tiles;

    public GameScreen(Minefield field) {
        //setLayout(new GridLayout(field.getWidth(), field.getHeight(), 0, 0 ));
        setLayout(new MigLayout("", "[][]"));
        setBorder(BorderFactory.createLoweredBevelBorder());

        int size = field.getWidth() * field.getHeight();
        tiles = new JButton[size];

        for(int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                int index = field.getWidth() * y + x;
                boolean isMine = field.getTile(index).isMine();
                TileButton tile = new TileButton(field.getTile(index));
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

        public TileButton(Tile tile) {
            setPreferredSize(new Dimension(16, 16));
            setMargin(new Insets(0,0, 0, 0));
            setIcon(MenuIcons.EMPTY);
            setBorder(BorderFactory.createEmptyBorder());
            setSelectedIcon((tile.isMine() ? MenuIcons.MINE : MenuIcons.NUMBER_1));
            this.tile = tile;
        }

        @Override
        public void setDisabledIcon(Icon icon) {
            return;
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
                //tile.setBackground(Color.RED);
                tile.setIcon(MenuIcons.MINE);
            } else {
                tile.setIcon(MenuIcons.NUMBER_1);
            }
        }
    }

}
