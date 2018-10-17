package com.crypticvortex.minesweeper.menus;

import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Display area for Mine counter and Score counter.
 *
 * @author Jatboy
 */
public class CounterPanel extends JPanel {
    private String score, mines;
    private JLabel[] mineDigits;
    private JLabel[] scoreDigits;
    private FaceButton button;

    public CounterPanel(int width) {
        setLayout(new MigLayout(new LC().insets("10", "5", "10", "5").gridGap("0", "0")));
        setSize(width, 50);
        setBorder(BorderFactory.createLoweredBevelBorder());

        mineDigits = new JLabel[3];
        mineDigits[0] = new JLabel();
        mineDigits[1] = new JLabel();
        mineDigits[2] = new JLabel();

        scoreDigits = new JLabel[3];
        scoreDigits[0] = new JLabel();
        scoreDigits[1] = new JLabel();
        scoreDigits[2] = new JLabel();

        for(int m = 0; m < mineDigits.length; m++)
            add(mineDigits[m], (m == mineDigits.length - 1 ? "gapright 20, cell " + m + " 0" : "cell " + m + " 0"));

        button = new FaceButton();
        button.setPreferredSize(new Dimension(26, 26));
        add(button, "w 26!, h 26!, gapright 20");

        for(int s = 0; s < scoreDigits.length; s++)
            add(scoreDigits[s], "cell " + (5 + s) + " 0");

        score = "000";
        mines = "010";

        setDigits();
        /**
         * End result:
         * 3 digit counter displayed from images on either side; one for mines the other for score
         * Face button in the center which changes expression based on game state
         */
    }

    private class FaceButton extends JButton {
        public FaceButton() {
            setFocusPainted(false);
            setSize(26, 26);
            setIcon(MenuIcons.FACE_SMILEY);
            setSelectedIcon(MenuIcons.FACE_SMILEY_PRESS);
            addMouseListener(new FaceMouseListener());
        }

        public void flashEmotion(int emotion) {
            switch(emotion) {//0 - Nervous, 1 = Dead, 2 = Cool
                case 0:
                    button.setIcon(MenuIcons.FACE_NERVOUS);
                    break;
                case 1:
                    button.setIcon(MenuIcons.FACE_DEAD);
                    break;
                case 2:
                    button.setIcon(MenuIcons.FACE_COOL);
                    break;
            }
            System.out.println("Hello World!");
            /*if(emotion == 0)
                button.setIcon(MenuIcons.FACE_SMILEY);*/
        }

        private class FaceMouseListener extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                setIcon(MenuIcons.FACE_SMILEY_PRESS);
            }
            public void mouseReleased(MouseEvent e) {
                setIcon(MenuIcons.FACE_SMILEY);
            }
        }
    }

    /**
     * Used for conveying facial expressions when performing actions, will always quickly reset back to default after updating.
     * @param emotion Numerical value of desired face.
     */
    public void updateFace(int emotion) {
       button.flashEmotion(emotion);
    }

    public void setDigits() {
        int digit = 0;
        for(char c : score.toCharArray()) {
            switch(c) {
                case '0': scoreDigits[digit].setIcon(MenuIcons.COUNTER_0); break;
                case '1': scoreDigits[digit].setIcon(MenuIcons.COUNTER_1); break;
                case '2': scoreDigits[digit].setIcon(MenuIcons.COUNTER_2); break;
                case '3': scoreDigits[digit].setIcon(MenuIcons.COUNTER_3); break;
                case '4': scoreDigits[digit].setIcon(MenuIcons.COUNTER_4); break;
                case '5': scoreDigits[digit].setIcon(MenuIcons.COUNTER_5); break;
                case '6': scoreDigits[digit].setIcon(MenuIcons.COUNTER_6); break;
                case '7': scoreDigits[digit].setIcon(MenuIcons.COUNTER_7); break;
                case '8': scoreDigits[digit].setIcon(MenuIcons.COUNTER_8); break;
                case '9': scoreDigits[digit].setIcon(MenuIcons.COUNTER_9); break;
                case '-': scoreDigits[digit].setIcon(MenuIcons.COUNTER_DASH); break;
            }
            digit++;
        }

        digit = 0;
        for(char c : mines.toCharArray()) {
            switch(c) {
                case '0': mineDigits[digit].setIcon(MenuIcons.COUNTER_0); break;
                case '1': mineDigits[digit].setIcon(MenuIcons.COUNTER_1); break;
                case '2': mineDigits[digit].setIcon(MenuIcons.COUNTER_2); break;
                case '3': mineDigits[digit].setIcon(MenuIcons.COUNTER_3); break;
                case '4': mineDigits[digit].setIcon(MenuIcons.COUNTER_4); break;
                case '5': mineDigits[digit].setIcon(MenuIcons.COUNTER_5); break;
                case '6': mineDigits[digit].setIcon(MenuIcons.COUNTER_6); break;
                case '7': mineDigits[digit].setIcon(MenuIcons.COUNTER_7); break;
                case '8': mineDigits[digit].setIcon(MenuIcons.COUNTER_8); break;
                case '9': mineDigits[digit].setIcon(MenuIcons.COUNTER_9); break;
                case '-': mineDigits[digit].setIcon(MenuIcons.COUNTER_DASH); break;
            }
            digit++;
        }
    }

}
