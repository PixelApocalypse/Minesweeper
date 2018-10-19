package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Display area for Mine counter and Score counter.
 *
 * @author Jatboy
 */
public class CounterPanel extends JPanel {
    //private Thread timer;
    private Thread thread;
    private Minefield field;
    private boolean completed;
    private int score, mines;
    private FaceButton button;
    private JLabel[] mineDigits;
    private JLabel[] scoreDigits;
    private boolean isTimerWaiting;
    private boolean isTimerCounting;
    private final int TIMER_UPDATE_RATE = 250;

    public CounterPanel(Minefield field) {
        this.field = field;

        setLayout(new MigLayout(new LC().insets("10", "5", "10", "5").gridGap("0", "0")));
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
        int btnSize = 26;
        switch(field.getScale()) {
            case DEFAULT:
                btnSize = 26;
                break;
            case TIMES_2:
                btnSize *= 2;
                break;
        }
        add(button, "w " + btnSize + "!, h " + btnSize + "!, gapright 20, cell 4 0");

        for(int s = 0; s < scoreDigits.length; s++)
            add(scoreDigits[s], "cell " + (5 + s) + " 0");

        score = 0;
        mines = field.getMineCount();
        setDigits();

        isTimerCounting = false;
        isTimerWaiting = true;

        new Thread(() -> {
            int timeSinceLastSecond = 0;
            int totalTime = 0;
            long lastUpdate = System.currentTimeMillis();

            while(true){
                try{
                    if(!isTimerCounting){
                        isTimerWaiting = true;
                        do{
                            Thread.sleep(TIMER_UPDATE_RATE);
                        } while(!isTimerCounting);
                        isTimerWaiting = false;
                        timeSinceLastSecond = 0;
                        totalTime = 0;
                        lastUpdate = System.currentTimeMillis();
                    }
                    Thread.sleep(TIMER_UPDATE_RATE);
                    timeSinceLastSecond += (int) System.currentTimeMillis() - lastUpdate;

                    while(timeSinceLastSecond >= 1000) {
                        totalTime += 1;
                        timeSinceLastSecond -= 1000;
                        if(totalTime > 999)
                            totalTime = 999;
                    }

                    this.score = Math.min(totalTime, 999);
                    setDigits();
                    if (thread != null) {
                        if (completed) {
                            completed = false;
                            thread.join();
                            thread = null;
                        }
                    }
                    lastUpdate = System.currentTimeMillis();
                } catch(Exception ex){
                    System.out.println(ex);
                }
            }
        }).start();
    }

    public void resetButton() {
        remove(button);
        button.resetSize();
        int btnSize = 26;
        switch(field.getScale()) {
            case DEFAULT:
                btnSize = 26;
                break;
            case TIMES_2:
                btnSize *= 2;
                break;
        }
        add(button, "w " + btnSize + "!, h " + btnSize + "!, gapright 20, cell 4 0");
    }

    public void startTimer(){
        while(!isTimerWaiting) {}
        isTimerCounting = true;
    }

    public void stopTimer(){
        isTimerCounting = false;
    }

    /**
     * Used for conveying facial expressions when performing actions, will always quickly reset back to default after updating.
     * @param emotion Numerical value of desired face.
     */
    public void updateFace(int emotion) {
        button.setEmotion(emotion);
    }

    /**
     * Reinitalizes the Minefield variable.
     * @param field New reference.
     */
    public void setField(Minefield field) {
        this.field = field;
        int mineCount = this.field.getMineCount();
        mines = mineCount;
        setDigits();
    }

    public void increaseMines() {
        mines++;
        setDigits();
    }

    /**
     * Decrease the number of mines the counter displays.
     */
    public void decreaseMines() {
        mines--;
        setDigits();
    }

    /**
     * Changes the icon for each of the three digits by breaking down the character arrays.
     */
    public void setDigits() {
        int sDigit = 0;
        String score = String.format("%03o", this.score);
        if(score.length() > 3)
            score = score.substring(0, 3);
        for(char c : score.toCharArray()) {
            String icon = "";
            switch(c) {
                case '0': icon = MenuIcons.COUNTER_0; break;
                case '1': icon = MenuIcons.COUNTER_1; break;
                case '2': icon = MenuIcons.COUNTER_2; break;
                case '3': icon = MenuIcons.COUNTER_3; break;
                case '4': icon = MenuIcons.COUNTER_4; break;
                case '5': icon = MenuIcons.COUNTER_5; break;
                case '6': icon = MenuIcons.COUNTER_6; break;
                case '7': icon = MenuIcons.COUNTER_7; break;
                case '8': icon = MenuIcons.COUNTER_8; break;
                case '9': icon = MenuIcons.COUNTER_9; break;
                case '-': icon = MenuIcons.COUNTER_DASH; break;
            }
            scoreDigits[sDigit].setIcon(MenuIcons.getScaledIcon(field.getScale(), icon));
            sDigit++;
        }

        int digit = 0;
        String mines = String.format("%03d", ((this.mines <= -100 && this.mines < 0) ? -99 : this.mines));
        if(mines.length() > 3)
            mines = mines.substring(0, 3);
        for(char c : mines.toCharArray()) {
            String icon = "";
            switch(c) {
                case '0': icon = MenuIcons.COUNTER_0; break;
                case '1': icon = MenuIcons.COUNTER_1; break;
                case '2': icon = MenuIcons.COUNTER_2; break;
                case '3': icon = MenuIcons.COUNTER_3; break;
                case '4': icon = MenuIcons.COUNTER_4; break;
                case '5': icon = MenuIcons.COUNTER_5; break;
                case '6': icon = MenuIcons.COUNTER_6; break;
                case '7': icon = MenuIcons.COUNTER_7; break;
                case '8': icon = MenuIcons.COUNTER_8; break;
                case '9': icon = MenuIcons.COUNTER_9; break;
                case '-': icon = MenuIcons.COUNTER_DASH; break;
            }
            mineDigits[digit].setIcon(MenuIcons.getScaledIcon(field.getScale(), icon));
            digit++;
        }
    }

    public boolean isTimerCounting() {
        return isTimerCounting;
    }

    private class FaceButton extends JButton {
        public FaceButton() {
            setFocusPainted(false);
            setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_SMILEY));
            addMouseListener(new FaceMouseListener());
            addActionListener((e) -> {
                score = 0;
                setDigits();
                Application.get.createField();
            });
        }

        public void resetSize() {
            setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_SMILEY));
        }

        public void setEmotion(int emotion) {
            thread = new Thread(() -> {
                try {
                    switch (emotion) {//0 - Nervous, 1 = Dead, 2 = Cool
                        case 0:
                            button.setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_NERVOUS));
                            break;
                        case 1:
                            button.setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_DEAD));
                            break;
                        case 2:
                            button.setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_COOL));
                            break;
                    }
                    if (emotion == 0) {
                        Thread.sleep(100);
                        button.setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_SMILEY));
                        completed = true;
                    }
                } catch (Exception ex) {
                }
            });
            thread.start();
        }

        private class FaceMouseListener extends MouseAdapter {
            public void mousePressed(MouseEvent e) {
                setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_SMILEY_PRESS));
            }
            public void mouseReleased(MouseEvent e) {
                setIcon(MenuIcons.getScaledIcon(field.getScale(), MenuIcons.FACE_SMILEY));
            }
        }
    }

}
