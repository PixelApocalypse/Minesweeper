package com.crypticvortex.minesweeper.menus;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * Display area for Mine counter and Score counter.
 *
 * @author Jatboy
 */
public class CounterPanel extends JPanel {
    private String score, mines;

    // Mine digits
    private JLabel mine_dig1;
    private JLabel mine_dig2;
    private JLabel mine_dig3;
    // Score digits
    private JLabel score_dig1;
    private JLabel score_dig2;
    private JLabel score_dig3;

    public CounterPanel() {
        setLayout(new MigLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());

        /**
         * End result:
         * 3 digit counter displayed from images on either side; one for mines the other for score
         * Face button in the center which changes expression based on game state
         */
    }

    public void setDigits() {
        int score_1 = Integer.parseInt(score.substring(0, 1));

    }

}
