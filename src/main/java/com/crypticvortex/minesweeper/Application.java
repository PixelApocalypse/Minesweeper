package com.crypticvortex.minesweeper;

import com.crypticvortex.minesweeper.mechanics.Difficulty;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.menus.CounterPanel;
import com.crypticvortex.minesweeper.menus.DifficultyDialog;
import com.crypticvortex.minesweeper.menus.GameScreen;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Application window.
 *
 * @author Jatboy
 */
public class Application extends JFrame {
    private Minefield field;
    private GameScreen screen;
    private Difficulty currentDiff;

    private int startX = 0, startY = 0;

    public Application() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new MigLayout());
        setResizable(false);

        startX = getX();
        startY = getY();

        try {
            setIconImage(new ImageIcon(Application.class.getResource("/images/favicon.png")).getImage());
        } catch (Exception ex) {}

        field = new Minefield(9, 9, 10, Difficulty.BEGINNER); // Beginner
        field.populate();

        createMenuBar();

        counter = new CounterPanel(field);
        add(counter, "center, wrap");

        screen = new GameScreen(field, counter);
        add(screen, "center");
        pack();

        get = this;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createField();
            }
        });
        newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        gameMenu.add(newGame);
        gameMenu.addSeparator();

        // ---- Difficulties ----
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
        // ---- End Difficulties ----

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    public void createField() {
        try{

            counter.stopTimer();
        } catch(Exception ex){

        }
        field = new Minefield(
                ((currentDiff == Difficulty.CUSTOM) || (currentDiff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.width : currentDiff.getColumns(),
                ((currentDiff == Difficulty.CUSTOM) || (currentDiff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.height : currentDiff.getRows(),
                ((currentDiff == Difficulty.CUSTOM) || (currentDiff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.mines : currentDiff.getMines(),
                currentDiff);
        field.populate();
        remove(screen);
        screen = new GameScreen(field, counter);
        add(screen, "center");
        counter.setField(field);
        counter.setDigits();
        pack();
        if(currentDiff == Difficulty.EXPERIMENTAL || currentDiff == Difficulty.CUSTOM)
            setLocationRelativeTo(null);
    }

    /**
     * @return Local minefield variable.
     */
    public Minefield getMinefield() {
        return field;
    }

    /**
     * @return Application's main logger.
     */
    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        Application app = new Application();
        app.setVisible(true);
        app.requestFocus();;
    }

    private class DifficultyListener implements ChangeListener {
        private JCheckBoxMenuItem item1, item2, item3;

        public DifficultyListener(JCheckBoxMenuItem item1, JCheckBoxMenuItem item2, JCheckBoxMenuItem item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
        }

        public void stateChanged(ChangeEvent e) {
            if(e.getSource() == item1 && item1.isSelected()) {
                item2.setSelected(false);
                item3.setSelected(false);
                currentDiff = Difficulty.BEGINNER;
            } else if(e.getSource() == item2 && item2.isSelected()) {
                item1.setSelected(false);
                item3.setSelected(false);
                currentDiff = Difficulty.INTERMEDIATE;
            } else if(e.getSource() == item3 && item3.isSelected()) {
                item1.setSelected(false);
                item2.setSelected(false);
                currentDiff = Difficulty.EXPERT;
            }
        }
    }

    public static Application get;
    public static CounterPanel counter;
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger("Minesweeper");
}
