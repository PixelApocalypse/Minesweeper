package com.crypticvortex.minesweeper;

import com.crypticvortex.minesweeper.mechanics.Difficulty;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.menus.GameScreen;
import com.crypticvortex.minesweeper.menus.StartScreen;

import javax.swing.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application extends JFrame {
    private Minefield field;

    /**
     * CONTROLS:
     * Left click to reveal a square.
     * Right click to place a flag to mark mine locations.
     * Shift + Right Click to cycle flag color.
     * N to open the new game window.
     * R to reset your current game back to the start.
     *
     * MECHANICS:
     * Scale the amount of mines to the size of the playing field on a percentile of total tiles.
     * Never duplicate mine positions.
     * Reveal large portions of empty squares on one click.
     * Numbers based on adjacent mine count (all 8 squares); Blue for 1, Green for 2, Red for 3.
     * Reveal all mines on defeat.
     * Elapsed time counter.
     * Approximate mine counter.
     */

    public Application() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        field = new Minefield(16, Difficulty.MEDIUM);
        field.populate();

//        setContentPane(new StartScreen());
        setContentPane(new GameScreen(field));
        pack();
    }

    public Minefield getMinefield() {
        return field;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Application app = new Application();
        app.setVisible(true);
    }

    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger("Minesweeper");
}
