package com.crypticvortex.minesweeper;

import com.crypticvortex.minesweeper.menus.StartScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Application extends JFrame {
    private static final long serialVersionUID = -1L;

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
        setContentPane(new StartScreen());

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { // Called after the button has been pressed and released.

            }

            public void mousePressed(MouseEvent e) { // Called when the mouse button is first pressed down.

            }

            public void mouseReleased(MouseEvent e) { // Called when the mouse button is released after being pressed.

            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Application app = new Application();
        app.setVisible(true);
    }

}
