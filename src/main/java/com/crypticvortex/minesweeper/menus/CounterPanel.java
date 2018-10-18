package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import javafx.concurrent.Worker;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Display area for Mine counter and Score counter.
 *
 * @author Jatboy
 */
public class CounterPanel extends JPanel {
    private final int TIMER_UPDATE_RATE = 250;

    private Thread timer;
    private Thread thread;
    private Minefield field;
    private boolean completed;
    private boolean isTimerWaiting;
    private boolean isTimerCounting;
    private FaceButton button;
    private String score, mines;
    private JLabel[] mineDigits;
    private JLabel[] scoreDigits;


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
        button.setPreferredSize(new Dimension(26, 26));
        add(button, "w 26!, h 26!, gapright 20");

        for(int s = 0; s < scoreDigits.length; s++)
            add(scoreDigits[s], "cell " + (5 + s) + " 0");

        score = "000";
        int mineCount = field.getMineCount();
        mines = (mineCount < 100 ? "0" : "") + mineCount;
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
                        System.out.println("Timer stopped");
                        do{
                            Thread.sleep(TIMER_UPDATE_RATE);
                        } while(!isTimerCounting);
                        isTimerWaiting = false;
                        timeSinceLastSecond = 0;
                        totalTime = 0;
                        lastUpdate = System.currentTimeMillis();
                        System.out.println("Timer started");
                    }
                    Thread.sleep(TIMER_UPDATE_RATE);
                    timeSinceLastSecond += (int) System.currentTimeMillis() - lastUpdate;

                    while(timeSinceLastSecond >= 1000) {
                        totalTime += 1;
                        timeSinceLastSecond -= 1000;
                        if(totalTime > 999)
                            totalTime = 999;
                    }

                    if(totalTime < 100)
                        this.score = (totalTime < 10 ? "00" : "0") + Math.min(totalTime, 999);
                    else
                        this.score = "" + Math.min(totalTime, 999);
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

    public boolean isTimerCounting() { return isTimerCounting; }

    public void startTimer(){
        while(!isTimerWaiting) {System.out.println("Waiting for timer to be ready to start.");}
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
        button.flashEmotion(emotion);
    }

    /**
     * Reinitalizes the Minefield variable.
     * @param field New reference.
     */
    public void setField(Minefield field) {
        this.field = field;
        int mineCount = this.field.getMineCount();
        mines = (mineCount < 100 ? "0" : "") + mineCount;
        setDigits();
    }

    public void increaseMines() {
        int count = Integer.parseInt(mines.replaceFirst("^0+(?!$)", ""));
        count++;
        StringBuilder builder = new StringBuilder();
        if(count < 0) {
            builder.append(count > -10 ? "0" : "");
        } else
            builder.append(count < 10 ? "00" : "0");
        builder.append(count);
        this.mines = builder.toString();
        setDigits();
    }

    /**
     * Decrease the number of mines the counter displays.
     */
    public void decreaseMines() {
        int count = Integer.parseInt(mines.replaceFirst("^0+(?!$)", ""));
        count--;
        StringBuilder builder = new StringBuilder();
        if(count < 0) {
            builder.append(count > -10 ? "0" : "");
        } else
            builder.append(count < 10 ? "00" : "0");
        builder.append(count);
        this.mines = builder.toString();
        setDigits();
    }

    /**
     * Changes the icon for each of the three digits by breaking down the character arrays.
     */
    public void setDigits() {
        int sDigit = 0;
        for(char c : score.toCharArray()) {
            switch(c) {
                case '0': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_0); break;
                case '1': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_1); break;
                case '2': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_2); break;
                case '3': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_3); break;
                case '4': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_4); break;
                case '5': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_5); break;
                case '6': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_6); break;
                case '7': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_7); break;
                case '8': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_8); break;
                case '9': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_9); break;
                case '-': scoreDigits[sDigit].setIcon(MenuIcons.COUNTER_DASH); break;
            }
            sDigit++;
        }

        int digit = 0;
        if(Integer.parseInt(mines) > 999)
            mines = "999";
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

    private class FaceButton extends JButton {
        public FaceButton() {
            setFocusPainted(false);
            setSize(26, 26);
            setIcon(MenuIcons.FACE_SMILEY);
            setSelectedIcon(MenuIcons.FACE_SMILEY_PRESS);
            addMouseListener(new FaceMouseListener());
            addActionListener((e) -> {
                score = "000";
                setDigits();
                Application.get.createField();
            });
        }

        public void flashEmotion(int emotion) {
            thread = new Thread(() -> {
                try {
                    switch (emotion) {//0 - Nervous, 1 = Dead, 2 = Cool
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
                    if (emotion == 0) {
                        Thread.sleep(100);
                        button.setIcon(MenuIcons.FACE_SMILEY);
                        completed = true;
                    }
                } catch (Exception ex) {
                }
            });
            thread.start();
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

}
