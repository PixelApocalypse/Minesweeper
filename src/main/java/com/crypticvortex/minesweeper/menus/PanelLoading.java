package com.crypticvortex.minesweeper.menus;

import com.sun.jmx.snmp.tasks.Task;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PanelLoading extends JFrame {
    private int tileCount;
    private JLabel progressText;
    private JProgressBar progress;


    public PanelLoading() {
        setResizable(false);
        setLayout(new MigLayout());

        progressText = new JLabel("");
        progress = new JProgressBar();

        add(progressText, "w 150!, center, wrap");
        add(progress, "w 150!");
        pack();

        setSize(200, 100);
        instance = this;
    }

    private void reset(int tileCount) {
        setTileCount(tileCount);
        progress.setMaximum(tileCount);
        progress.setValue(0);
    }

    private void incrementProgress(int state) { // States: 0 - Generating tiles, 1 - Creating images
        int newValue = progress.getValue() + 1;
        progressText.setText((state ==0?"Generating tiles ("+(progress.getValue()*100.0f)/tileCount +"%)":"Creating sprites ("+(progress.getValue()*100.0f)/tileCount +"%)"));
        progress.setValue(newValue);
    }

    private void setTileCount(int tileCount) {
        instance.tileCount = tileCount;
        progress.setMaximum(tileCount);
        System.out.println("setTileCount() = " + this.tileCount);
    }

    public static void increment(int state) {
        instance.incrementProgress(state);
    }

    public static void show(int tileCount) {
        instance.reset(tileCount);
        instance.setTileCount(tileCount);
        instance.setVisible(true);
        instance.setLocationRelativeTo(null);
    }

    public static void finish(){
        instance.setVisible(false);
    }

    static {
        new PanelLoading();
    }

    private static PanelLoading instance;
}
