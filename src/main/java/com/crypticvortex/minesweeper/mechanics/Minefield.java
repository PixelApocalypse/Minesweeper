package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.menus.CounterPanel;
import com.crypticvortex.minesweeper.menus.DifficultyDialog;
import com.crypticvortex.minesweeper.menus.MenuIcons;
import com.crypticvortex.minesweeper.menus.PanelLoading;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Stores primary game data
 *
 * @author Caraibe8
 * @author Jatboy
 */
public class Minefield {
    private int seed;
    private Tile[] tiles;
    private GameScale scale;
    private Difficulty diff;
    private boolean gameFinished;
    private int width, height, mineCount;
    private List<Integer> mineCoordinates;

    /**
     * Create a new minefield with the given parameters:
     * @param diff the difficulty of the minefield.
     * @param scale the scale of the minefield images.
     */
    public Minefield(Difficulty diff, GameScale scale) {
        this.seed = new Random().nextInt();
        this.diff = diff;
        this.scale = scale;

        this.width = ((diff == Difficulty.CUSTOM) || (diff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.width : diff.getColumns();
        this.height = ((diff == Difficulty.CUSTOM) || (diff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.height : diff.getRows();
        this.mineCount = ((diff == Difficulty.CUSTOM) || (diff == Difficulty.EXPERIMENTAL)) ? DifficultyDialog.mines : diff.getMines();
        if(width < 2) width = 2;
        if(height < 2) height = 2;
        if(mineCount < 1) mineCount = 1;

        this.tiles = new Tile[width * height];
    }

    /**
     * Generates tiles and put them in the minefield.
     */
    public void populate(boolean firstRun) {
        this.mineCoordinates = null;
        createMines();
        if(!firstRun)
            PanelLoading.show(tiles.length);
        for(int i = 0; i < width * height; ++i) {
            tiles[i] = new Tile(i, mineCoordinates.contains(i), scale);
            if (!firstRun)
                PanelLoading.increment(0 /* Generating tiles */);
            System.out.println(i + "/" + tiles.length);
        }
        PanelLoading.increment(0 /* Generating tiles */);
        PanelLoading.finish();
    }

    /**
     * Creates and returns the index of all mines.
     * @return List of mine coordinates
     */
    private ArrayList<Integer> createMines(){
        Random random = new Random(this.seed);
        int nbOfMines;
        if(diff == Difficulty.EXPERIMENTAL) {
            nbOfMines = Math.round(tiles.length * ((float) (DifficultyDialog.mines) / 100));
        } else
            nbOfMines = mineCount;
        if(nbOfMines < 1)
            nbOfMines = 1;
        mineCount = nbOfMines;

        ArrayList<Integer> minesCoordinate = new ArrayList<>(nbOfMines);
        for(int i = 0; i < nbOfMines; i++) {
            int mineLocation = random.nextInt(tiles.length);
            if (minesCoordinate.contains(mineLocation)) {
                i--;
                continue;
            }
            minesCoordinate.add(mineLocation);
        }
        this.mineCoordinates = minesCoordinate;
        return minesCoordinate;
    }

    /**
     * Returns the tile at the given index.
     * @param index of the mine to return
     * @return copy of the tile at the given index.
     */
    public Tile getTile(int index){
        return tiles[index];
    }

    /**
     * Shows the tile at the given index.
     * @param index of the tile to show
     * @return true if a mine was shown
     */
    public boolean showTile(int index, boolean pressed) {
        Tile tile = tiles[index];
        if(gameFinished && pressed)
            return false;

        if(tile.getFlagType() != FlagType.INVALID)
            return false;

        if(tile.isShown() && getNearbyMines(index) == getNearbyFlags(index)) {
            boolean mineRevealed = false;
            for(Tile _tile : getNearbyTiles(index)) {
                if (!_tile.isShown())
                    if(showTile(_tile.getId(), true))
                        mineRevealed = true;
            }
            return true;
        }
        if(getNearbyMines(index) != 0 || tile.isMine()) {
            showSingleTile(tile, pressed);
            return tile.isMine();
        } else
            showMultipleTiles(tile);

        return false;
    }

    private void showSingleTile(Tile tile, boolean pressed){
        if(tile.showTile()) {
            if(tile.isMine()) {
                if(pressed)
                    tile.setIcon(MenuIcons.getScaledIcon(scale, MenuIcons.MINE_PRESSED));
                else
                    tile.setIcon(MenuIcons.getScaledIcon(scale, MenuIcons.MINE));
                gameFinished = true;
            } else {
                int mines = getNearbyMines(tile.getId());
                tile.setIcon(MenuIcons.getIconFrom(scale, mines));
            }
        }
    }

    private void showMultipleTiles(Tile source){
        ArrayList<Tile> tilesToReveal = new ArrayList<>();
        tilesToReveal.add(source);
        ArrayList<Tile> tilesToCheck = new ArrayList<>();
        tilesToCheck.add(source);

        for(int i = 0; i < tilesToCheck.size(); i++){
            for(Tile tile : getNearbyTiles(tilesToCheck.get(i).getId())){
                if(tilesToReveal.contains(tile))
                    continue;
                tilesToReveal.add(tile);
                if(getNearbyMines(tile.getId()) == 0)
                    tilesToCheck.add(tile);
            }
        }

        for(Tile tile : tilesToReveal)
            showSingleTile(tile, true);
    }

    /**
     * Gets and returns the amount of mines around the tile at the given index.
     * @param index of the tile around which to look
     * @return number of nearby mines
     */
    public int getNearbyMines(int index){
        int nearbyMines = 0;
        for(Tile tile : getNearbyTiles(index)){
            if(tile.isMine())
                nearbyMines++;
        }
        return nearbyMines;
    }

    public int getNearbyFlags(int index){
        int nearbyFlags = 0;
        for(Tile tile : getNearbyTiles(index)){
            if(tile.getFlagType() != FlagType.INVALID)
                ++nearbyFlags;
        }
        return nearbyFlags;
    }

    /**
     * Verifies if the game is won.
     * @return is the game finished
     */
    public boolean gameWon(){
        for(int i = 0; i < tiles.length; ++i) {
            if (!tiles[i].isShown() && !tiles[i].isMine())
                return false;
        }
        gameFinished = true;
        return true;
    }

    /**
     * Gets and returns all tiles around the tile at the given index.
     * @param index index of the tile to look around
     * @return all tiles around the given tile
     */
    private Tile[] getNearbyTiles(int index) {
        ArrayList<Tile> nearbyTiles = new ArrayList<>();

        if (index - width - 1 >= 0 && (index - width - 1) / width == (index - width) / width)
            nearbyTiles.add(tiles[index - width - 1]);
        if (index - width >= 0)
            nearbyTiles.add(tiles[index - width]);
        if (index - width + 1 >= 0 && (index - width + 1) / width == (index - width) / width)
            nearbyTiles.add(tiles[index - width + 1]);

        if (index - 1 >= 0 && (index - 1) / width == index / width)
            nearbyTiles.add(tiles[index - 1]);
        if (index + 1 < tiles.length && (index + 1) / width == index / width)
            nearbyTiles.add(tiles[index + 1]);

        if (index + width - 1 < tiles.length && (index + width - 1) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width - 1]);
        if (index + width < tiles.length && (index + width) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width]);
        if (index + width + 1 < tiles.length && (index + width + 1) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width + 1]);

        Tile[] optimisedNearbyTiles = nearbyTiles.toArray(new Tile[]{});
        return optimisedNearbyTiles;
    }

    public boolean plantFlag(int index) {
        if(gameFinished)
            return false;

        Tile tile = tiles[index];
        if(!tile.isShown()) {
            if (tile.getFlagType() == FlagType.INVALID) {
                tile.setFlagType(FlagType.RED);
                return true;
            } else if (tile.getFlagType() != FlagType.QUESTION) {
                tile.setFlagType(FlagType.QUESTION);
                return false;
            } else if (tile.getFlagType() == FlagType.QUESTION) {
                tile.setFlagType(FlagType.INVALID);
                return false;
            }
        }
        return false;
    }

    /**
     * Registers the mouse listener for each Tile.
     * @param index Coordinate of target tile.
     */
    public void createTileMouseListener(int index){
        tiles[index].addMouseListener(new TileMouseListener(Application.counter));
    }

    public void cycleTileFlagColor(int index){
        if(gameFinished)
            return;
        tiles[index].cycleColor();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Integer> getMineCoordinates() {
        return mineCoordinates;
    }

    public int getMineCount() {
        return mineCoordinates.size();
    }

    public GameScale getScale() {
        return scale;
    }

    public Difficulty getDifficulty() {
        return diff;
    }

    private class TileMouseListener implements MouseListener {
        private CounterPanel panel;
        public TileMouseListener(CounterPanel panel) {
            this.panel = panel;
        }

        public void mousePressed(MouseEvent e) {
            Tile tile = (Tile) e.getSource();
            if (e.getButton() == MouseEvent.BUTTON3) {
                FlagType old = tile.getFlagType();
                if(plantFlag(tile.getId()))
                    panel.decreaseMines();
                else {
                    if(old != FlagType.INVALID && old != FlagType.QUESTION && !gameFinished)
                        panel.increaseMines();
                }
            }
            if (e.getButton() == MouseEvent.BUTTON2)
                cycleTileFlagColor(tile.getId());
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }

}
