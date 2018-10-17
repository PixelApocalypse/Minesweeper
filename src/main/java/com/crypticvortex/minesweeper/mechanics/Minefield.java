package com.crypticvortex.minesweeper.mechanics;

import com.crypticvortex.minesweeper.menus.CounterPanel;
import com.crypticvortex.minesweeper.menus.MenuIcons;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Stores primary game data.
 */
public class Minefield {
    private int seed;
    private Tile[] tiles;
    private Difficulty diff;
    private int width, height, mineCount;
    private final float MINE_PERCENT = 20f;
    private boolean gameFinished;

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
     * @param size the width and height of the minefield
     * @param diff the amount of mines depends on the difficulty
     */
    public Minefield(int size, Difficulty diff) {
        this(size, size, diff);
    }

    /**
     * Create a new minefield with the given parameters:
     * @param width Column count.
     * @param height Row count.
     * @param mineCount Amount of mines.
     */
    public Minefield(int width, int height, int mineCount) {
        this(width, height, Difficulty.CUSTOM);
        if(mineCount < 1)
            throw new IllegalArgumentException();
        this.mineCount = mineCount;
    }

    /**
     * Generates tiles and put them in the minefield.
     */
    public void populate() {
        ArrayList<Integer> minesCoordonate = getMinesIndex();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(i, minesCoordonate.contains(i));
        }
    }

    public void setTileMouseListener(int index, CounterPanel panel){
        tiles[index].addMouseListener(new TileMouseListener(panel));
    }

    private class TileMouseListener implements MouseListener {
        CounterPanel panel;
        public TileMouseListener(CounterPanel panel){
            super();
            this.panel = panel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            Tile tile = (Tile) e.getSource();
            if (e.getButton() == MouseEvent.BUTTON3) {
                plantFlag(tile.getId());
                panel.updateFace(0);
            }
            if (e.getButton() == MouseEvent.BUTTON2)
                tile.cycleColor();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

        /**
     * Gets and returns the index of all mines.
     * @return List of mine coordinates
     */
    private ArrayList<Integer> getMinesIndex(){
        Random random = new Random(this.seed);
        int nbOfMines;
        if(diff != Difficulty.CUSTOM)
            nbOfMines = Math.round(tiles.length * (MINE_PERCENT + diff.getAmount()) / 100);
        else
            nbOfMines = mineCount;
        if(this.mineCount > 0)
            nbOfMines = mineCount;
        ArrayList<Integer> minesCoordinate = new ArrayList<>(nbOfMines);

        for(int i = 0; i < nbOfMines; i++) {
            int mineLocation = random.nextInt(tiles.length);
            if (minesCoordinate.contains(mineLocation)) {
                i--;
                continue;
            }
            minesCoordinate.add(mineLocation);
        }
        return minesCoordinate;
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
    public boolean showTile(int index) {
        if(gameFinished)
            return true;

        Tile tile = tiles[index];
        if(tile.getFlagType() != FlagType.INVALID)
            return false;

        if(getNearbyMines(index) != 0 || tile.isMine()) {
            showSingleTile(tile);
            return tile.isMine();
        }

        else
            showMultipleTiles(tile);
        return false;
    }

    private void showSingleTile(Tile tile){
        if(tile.showTile()) {
            if(tile.isMine()) {
                tile.setIcon(MenuIcons.MINE_PRESSED);
                gameFinished = true;
            } else {
                int mines = getNearbyMines(getTileIndex(tile));
                switch(mines) {
                    case 1: tile.setIcon(MenuIcons.NUMBER_1); break;
                    case 2: tile.setIcon(MenuIcons.NUMBER_2); break;
                    case 3: tile.setIcon(MenuIcons.NUMBER_3); break;
                    case 4: tile.setIcon(MenuIcons.NUMBER_4); break;
                    case 5: tile.setIcon(MenuIcons.NUMBER_5); break;
                    case 6: tile.setIcon(MenuIcons.NUMBER_6); break;
                    case 7: tile.setIcon(MenuIcons.NUMBER_7); break;
                    case 8: tile.setIcon(MenuIcons.NUMBER_8); break;
                    default:
                        tile.setIcon(MenuIcons.EMPTY);
                        break;
                }
            }
        }
    }

    private void showMultipleTiles(Tile source){

        ArrayList<Tile> tilesToReveal = new ArrayList<>();
        tilesToReveal.add(source);
        ArrayList<Tile> tilesToCheck = new ArrayList<>();
        tilesToCheck.add(source);

        for(int i = 0; i < tilesToCheck.size(); i++){
            for(Tile tile : getNearbyTiles(getTileIndex(tilesToCheck.get(i)))){
                if(tilesToReveal.contains(tile))
                    continue;
                tilesToReveal.add(tile);
                if(getNearbyMines(getTileIndex(tile)) == 0)
                    tilesToCheck.add(tile);
            }
        }

        for(Tile tile : tilesToReveal)
            showSingleTile(tile);
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
                ++nearbyMines;
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

    public int[] getNearbyTilesIndex(int index){
        Tile[] nearbyTiles = getNearbyTiles(index);
        int[] nearbyTilesIndex = new int[nearbyTiles.length];
        for(int i = 0; i < nearbyTilesIndex.length; i++) {
            nearbyTilesIndex[i] = getTileIndex(nearbyTiles[i]);
        }

        return nearbyTilesIndex;
    }

    private int getTileIndex(Tile tile) {
        return Arrays.asList(tiles).indexOf(tile);
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

    public void plantFlag(int index) {
        if(gameFinished)
            return;

        Tile tile = tiles[index];
        if(!tile.isShown()) {
            if (tile.getFlagType() == FlagType.INVALID) {
                if(tile.setFlagType(FlagType.RED))
                    tile.setIcon(MenuIcons.FLAG_RED);
            } else if (tile.getFlagType() == FlagType.RED) {
                if(tile.setFlagType(FlagType.QUESTION))
                    tile.setIcon(MenuIcons.FLAG_QUESTION);
            } else if (tile.getFlagType() == FlagType.QUESTION) {
                if(tile.setFlagType(FlagType.INVALID))
                    tile.setIcon(MenuIcons.DEFAULT);
            }
        }
    }

    public void cycleTileFlagColor(int index){
        if(gameFinished)
            return;
        tiles[index].cycleColor();
    }
}
