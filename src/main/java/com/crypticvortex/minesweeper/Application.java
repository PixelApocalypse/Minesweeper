package com.crypticvortex.minesweeper;

import com.crypticvortex.minesweeper.mechanics.Difficulty;
import com.crypticvortex.minesweeper.mechanics.Minefield;
import com.crypticvortex.minesweeper.menus.GameScreen;

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

    /**
     * "✓" = Done, "-" = Under Reevaluation, "X" = Not Feasible, "\" = In-Progress
     *
     *                       ---- CONTROLS ----
     * [✓] Left click to reveal a square.
     * [ ] Right click to place a flag to mark mine locations.
     * [ ] Right click a flag to cycle it to a Question Mark, Right click that to remove it
     * [ ] Left + Right click on a revealed number with all mines marked to clear remaining adjacent squares.
     * [ ] Shift + Right Click to cycle flag color.
     * [ ] N to create a new game.
     *
     *                       ---- MECHANICS ----
     * [-] Scale the amount of mines to the size of the playing field on a percentile of total tiles.
     * [✓] Never duplicate mine positions.
     * [ ] Reveal large portions of empty squares on one click.
     * [ ] Numbers based on adjacent mine count (all 8 squares); Blue for 1, Green for 2, Red for 3.
     * [ ] Reveal all mines on defeat.
     * [ ] Elapsed time counter.
     * [ ] Approximate mine counter.
     *
     *                      ---- DIFFICULTIES ----
     * [✓] Beginner: 9x9 10 Mines
     * [✓] Intermediate: 16x16 40 Mines
     * [✓] Expert: 30x30 99 Mines
     * [ ] Custom: Minimum 7x7 10 Mines, Maximum Undetermined
     * [ ] Experimental: Similar to Custom, Percentile mine generation
     */

    public Application() {
        super("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setResizable(false);

        try {
            setIconImage(new ImageIcon(Application.class.getResource("/images/favicon.png")).getImage());
        } catch (Exception ex) {}

        field = new Minefield(9, 9, 10); // Beginner
        field.populate();

        createMenuBar();

        screen = new GameScreen(field);
        add(screen);
        pack();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(currentDiff) {
                    case BEGINNER:
                        field = new Minefield(9,9, 10);
                        break;
                    case INTERMEDIATE:
                        field = new Minefield(16, 16, 40);
                        break;
                    case EXPERT:
                        field = new Minefield(30, 30, 99);
                        break;
                    case CUSTOM:
                        // TODO : Make the Custom Difficulty dialog, store the information inputted there and fetch it here.
                        break;
                }
                field.populate();
                setVisible(false);
                remove(screen);
                screen = new GameScreen(field);
                add(screen);
                setVisible(true);
                pack();
            }
        });
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
        intermediate.addChangeListener(list);
        expert.addChangeListener(list);

        gameMenu.add(beginner);
        gameMenu.add(intermediate);
        gameMenu.add(expert);

        JMenuItem custom = new JMenuItem("Custom");
        gameMenu.add(custom);
        // ---- End Difficulties ----

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
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
            System.out.println(currentDiff);
        }
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

    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger("Minesweeper");
}
