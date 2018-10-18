package com.crypticvortex.minesweeper.menus;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.mechanics.Difficulty;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Small window for inputting custom grid sizes.
 *
 * @author Jatboy
 */
public class DifficultyDialog extends JFrame {
    private JTextField _height, _width, _mines, _mines_percent;

    public DifficultyDialog(Difficulty diff) {
        // Same class for two separate layouts.
        if(diff == Difficulty.CUSTOM) {
            setTitle("Custom");
            setLayout(new MigLayout());

            RangeVerifier verifier = new RangeVerifier();

            JLabel heightLbl = new JLabel("Height");
            add(heightLbl);

            _height = new JTextField("" + height);
            _height.setInputVerifier(verifier);
            _height.setToolTipText("Minimum: " + min_height);
            _height.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    height = Integer.parseInt(_height.getText());
                }
            });
            add(_height, "w 50!, wrap");

            JLabel widthLbl = new JLabel("Width");
            add(widthLbl);

            _width = new JTextField("" + width);
            _width.setInputVerifier(verifier);
            _width.setToolTipText("Minimum: " + min_width);
            _width.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    width = Integer.parseInt(_width.getText());
                }
            });
            add(_width, "w 50!, wrap");

            JLabel minesLbl = new JLabel("Mines");
            add(minesLbl);

            _mines = new JTextField("" + mines);
            _mines.setInputVerifier(verifier);
            _mines.setToolTipText("Minimum: " + min_mines);
            _mines.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    mines = Integer.parseInt(_mines.getText());
                }
            });
            add(_mines, "w 50!, wrap");

            JButton create = new JButton("Create");
            create.addActionListener((e) -> {
                width = Integer.parseInt(_width.getText());
                height = Integer.parseInt(_height.getText());
                mines = Integer.parseInt(_mines.getText());
                setVisible(false);
                Application.get.createField();
            });
            add(create);
            pack();
        } else if(diff == Difficulty.EXPERIMENTAL) {
            setTitle("Experimental");
            setLayout(new MigLayout());

            RangeVerifier verifier = new RangeVerifier();

            JLabel warning = new JLabel("<html><body><b style=\"color: red;\">WARNING:</b> Mines can go higher than 999 but the display is capped! " +
                    "<br/> Use at your own risk</body></html> <br/>");
            add(warning, "wrap");

            JLabel heightLbl = new JLabel("Height");
            add(heightLbl);

            _height = new JTextField("" + height);
            _height.setInputVerifier(verifier);
            _height.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    height = Integer.parseInt(_height.getText());
                }
            });
            _height.setToolTipText("Minimum: " + min_height);
            add(_height, "w 50!, wrap");

            JLabel widthLbl = new JLabel("Width");
            add(widthLbl);

            _width = new JTextField("" + width);
            _width.setInputVerifier(verifier);
            _width.setToolTipText("Minimum: " + min_width);
            _width.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    width = Integer.parseInt(_width.getText());
                }
            });
            add(_width, "w 50!, wrap");

            JLabel minesLbl = new JLabel("Mines");
            add(minesLbl);

            _mines_percent = new JTextField("10");
            _mines_percent.setInputVerifier(verifier);
            _mines_percent.addFocusListener(new FocusListener() {
                public void focusGained(FocusEvent e) {}
                public void focusLost(FocusEvent e) {
                    mines = Integer.parseInt(_mines_percent.getText());
                }
            });
            add(_mines_percent, "w 50!");

            JLabel minesPrcntLbl = new JLabel("%");
            add(minesPrcntLbl, "wrap");

            JButton create = new JButton("Create");
            create.addActionListener((e) -> {
                width = Integer.parseInt(_width.getText());
                height = Integer.parseInt(_height.getText());
                mines = Integer.parseInt(_mines_percent.getText());
                setVisible(false);
                Application.get.createField();
            });
            add(create);
            pack();
        }

        setLocationRelativeTo(Application.get);
    }

    private class RangeVerifier extends InputVerifier {
        private Border gray = BorderFactory.createLineBorder(Color.lightGray);
        private Border red = BorderFactory.createLineBorder(Color.red);

        protected boolean checkField(JComponent input) {
            if(input == _width) {
                boolean ret = Integer.parseInt(_width.getText()) >= min_width;
                if(!ret) input.setBorder(red);
                else input.setBorder(gray);
                return ret;
            } else if(input == _height) {
                boolean ret = Integer.parseInt(_height.getText()) >= min_height;
                if(!ret) input.setBorder(red);
                else input.setBorder(gray);
                return ret;
            } else if(input == _mines) {
                boolean ret = Integer.parseInt(_mines.getText()) >= min_mines;
                if(!ret) input.setBorder(red);
                else input.setBorder(gray);
                return ret;
            } else if(input == _mines_percent) {
                return true;
            }
            return true;
        }

        public boolean verify(JComponent input) {
            return checkField(input);
        }
    }

    public static int width = 7, height = 7, mines = 10;
    public static int min_width = 7, min_height = 7, min_mines = 10;
}