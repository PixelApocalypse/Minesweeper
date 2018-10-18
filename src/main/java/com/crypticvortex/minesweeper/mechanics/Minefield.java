package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.Application;
import com.crypticvortex.minesweeper.menus.CounterPanel;
import com.crypticvortex.minesweeper.menus.DifficultyDialog;
import com.crypticvortex.minesweeper.menus.MenuIcons;

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
    private Difficulty diff;
    private boolean gameFinished;
    private int width, height, mineCount;
    private float MINE_PERCENT = 20f;
    private List<Integer> mineCoordinates;

    /**
     * Create a new minefield with the given parameters:
     * @param width the width of the minefield
     * @param height the height of them minefield
     * @param diff the amount of mines depends on the difficulty
     */
    public Minefield(int width, int height, Difficulty diff){
        if(width < 2 || height < 2)
            throw new IllegalArgumentException();

        this.seed = new Random().nextInt();
        this.width = width;
        this.height = height;
        this.diff = diff;
        this.tiles = new Tile[width * height];
        this.mineCount = 0;
        this.gameFinished = false;
    }

    /**
     * Create a new minefield with the given parameters:
     * @param width Column count.
     * @param height Row count.
     * @param mineCount Amount of mines.
     */
    public Minefield(int width, int height, int mineCount, Difficulty diff) {
        this(width, height, diff);
        if(mineCount < 1)
            throw new IllegalArgumentException();
        this.mineCount = mineCount;
    }

    /**
     * Generates tiles and put them in the minefield.
     */
    public void populate() {
        this.mineCoordinates = null;
        createMines();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(i, mineCoordinates.contains(i));
        }
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
     * @return if the tile was successfully shown
     */
    public boolean showTile(int index, boolean pressed) {
        Tile tile = tiles[index];
        if(gameFinished && !tile.isMine())
            return true;

        if(tile.getFlagType() != FlagType.INVALID)
            return false;

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
                    tile.setIcon(MenuIcons.MINE_PRESSED);
                else
                    tile.setIcon(MenuIcons.MINE);
                gameFinished = true;
            } else {
                int mines = getNearbyMines(tile.getId());
                tile.setIcon(MenuIcons.getIconFrom(mines));
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

    public Difficulty getDifficulty() {
        return diff;
    }

    public List<Integer> getMineCoordinates() {
        return mineCoordinates;
    }

    public int getMineCount() {
        return mineCoordinates.size();
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
                    if(old != FlagType.INVALID && old != FlagType.QUESTION)
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
