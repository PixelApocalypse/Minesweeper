package com.crypticvortex.minesweeper;

import com.crypticvortex.minesweeper.mechanics.Difficulty;
import com.crypticvortex.minesweeper.mechanics.GameScale;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.menus.CounterPanel;
import com.crypticvortex.minesweeper.menus.DifficultyDialog;
import com.crypticvortex.minesweeper.menus.GameScreen;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Application window.
 *
 * @author Jatboy
 */
public class Application extends JFrame {
    private Minefield field;
    private GameScale scale;
    private GameScreen screen;
    private Difficulty currentDiff;
    private JScrollPane gameScreen;
    private boolean firstRun = true;

    public Application() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        setResizable(false);

        try {
            setIconImage(new ImageIcon(Application.class.getResource("/images/favicon.png")).getImage());
        } catch (Exception ex) {
        }

        scale = GameScale.DEFAULT;
        field = new Minefield(Difficulty.BEGINNER, scale);
        field.populate(true);

        createMenuBar();

        counter = new CounterPanel(field);
        add(counter, "center, wrap");

        screen = new GameScreen(field, counter);
        gameScreen = new JScrollPane(screen);
        add(gameScreen, "center");
        pack();

        get = this;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // ---- Game Menu ---- \\
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener((e) -> {
            createField();
        });
        newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        // ---- Difficulties ---- \\
        JCheckBoxMenuItem beginner = new JCheckBoxMenuItem("Beginner");
        JCheckBoxMenuItem intermediate = new JCheckBoxMenuItem("Intermediate");
        JCheckBoxMenuItem expert = new JCheckBoxMenuItem("Expert");
        DifficultyListener list = new DifficultyListener(beginner, intermediate, expert);

        beginner.setState(true);
        currentDiff = Difficulty.BEGINNER;

        beginner.addChangeListener(list);
        beginner.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        intermediate.addChangeListener(list);
        intermediate.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        expert.addChangeListener(list);
        expert.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

        gameMenu.add(beginner);
        gameMenu.add(intermediate);
        gameMenu.add(expert);

        JMenuItem custom = new JMenuItem("Custom");
        custom.addActionListener((e) -> {
            currentDiff = Difficulty.CUSTOM;
            JFrame frame = new DifficultyDialog(Difficulty.CUSTOM);
            frame.setVisible(true);
        });
        custom.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        gameMenu.add(custom);

        JMenuItem experimental = new JMenuItem("Experimental");
        experimental.addActionListener((e) -> {
            currentDiff = Difficulty.EXPERIMENTAL;
            JFrame frame = new DifficultyDialog(Difficulty.EXPERIMENTAL);
            frame.setVisible(true);
        });
        experimental.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        gameMenu.add(experimental);
        // ---- End Difficulties ---- \\
        // ---- End Game Menu ----\\
        menuBar.add(gameMenu);

        // ---- Scale Menu ---- \\
        JMenu scaleMenu = new JMenu("Scale");
        JCheckBoxMenuItem scaleX1 = new JCheckBoxMenuItem("Default");
        JCheckBoxMenuItem scaleX2 = new JCheckBoxMenuItem("Scale x2");

        scaleX1.setState(true);
        scaleX1.addChangeListener(new ScaleListener(scaleX1, scaleX2));
        scaleX2.addChangeListener(new ScaleListener(scaleX1, scaleX2));

        scaleMenu.add(scaleX1);
        scaleMenu.add(scaleX2);
        // ---- End Scale Menu ---- \\
        menuBar.add(scaleMenu);

        setJMenuBar(menuBar);
    }

    public void createField() {
        new Thread(() -> {
            try {
                counter.stopTimer();
            } catch (Exception ex) {
            }
            field = new Minefield(currentDiff, scale);
            field.populate(false);
            remove(gameScreen);

            screen = new GameScreen(field, counter);
            gameScreen = new JScrollPane(screen);
            add(gameScreen, "center");

            counter.setField(field);
            counter.resetButton();
            counter.setDigits();
            pack();
            Dimension finalDimension = new Dimension();
            Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            if (getSize().height > screenDimension.height)
                finalDimension.height = screenDimension.height;
            else
                finalDimension.height = getSize().height;
            if (getSize().width > screenDimension.width)
                finalDimension.width = screenDimension.width;
            else
                finalDimension.width = getSize().width;
            setSize(finalDimension);
            if (currentDiff == Difficulty.EXPERIMENTAL || currentDiff == Difficulty.CUSTOM)
                setLocationRelativeTo(null);
            if (currentDiff == Difficulty.EXPERT && field.getScale() == GameScale.TIMES_2)
                setLocationRelativeTo(null);
        }).start();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }

        Application app = new Application();
        app.setVisible(true);
        app.requestFocus();
        ;
    }

    private class DifficultyListener implements ChangeListener {
        private JCheckBoxMenuItem item1, item2, item3;

        public DifficultyListener(JCheckBoxMenuItem item1, JCheckBoxMenuItem item2, JCheckBoxMenuItem item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
        }

        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == item1 && item1.isSelected()) {
                item2.setSelected(false);
                item3.setSelected(false);
                currentDiff = Difficulty.BEGINNER;
            } else if (e.getSource() == item2 && item2.isSelected()) {
                item1.setSelected(false);
                item3.setSelected(false);
                currentDiff = Difficulty.INTERMEDIATE;
            } else if (e.getSource() == item3 && item3.isSelected()) {
                item1.setSelected(false);
                item2.setSelected(false);
                currentDiff = Difficulty.EXPERT;
            }
        }
    }

    private class ScaleListener implements ChangeListener {
        private JCheckBoxMenuItem item1, item2;

        public ScaleListener(JCheckBoxMenuItem item1, JCheckBoxMenuItem item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == item1 && item1.isSelected()) {
                item2.setSelected(false);
                scale = GameScale.DEFAULT;
            } else if (e.getSource() == item2 && item2.isSelected()) {
                item1.setSelected(false);
                scale = GameScale.TIMES_2;
            }
        }
    }

    public static Application get;
    public static CounterPanel counter;
    private static final long serialVersionUID = -1L;
}
