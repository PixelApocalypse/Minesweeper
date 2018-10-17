package com.crypticvortex.minesweeper.mechanics;

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

    /**
     * Create a new minefield with the given parameters:
     * @param width the width of the minefield
     * @param height the height of them minefield
     * @param diff the amount of mines depends on the difficulty
     */
    public Minefield(int width, int height, Difficulty diff){
        this.seed = new Random().nextInt();
        this.width = width;
        this.height = height;
        this.diff = diff;
        this.tiles = new Tile[width * height];
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
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.diff = Difficulty.CUSTOM;
        this.tiles = new Tile[width * height];
    }

    /**
     * Generates tiles and put them in the minefield.
     */
    public void populate() {
        ArrayList<Integer> minesCoordonate = getMinesIndex();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(minesCoordonate.contains(i));
        }
    }

    /**
     * Gets and returns the index of all mines.
     * @return List of mine coordinates
     */
    private ArrayList<Integer> getMinesIndex(){
        Random random = new Random(this.seed);
        int nbOfMines = Math.round(tiles.length * (MINE_PERCENT + diff.getAmount()) / 100);
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

    public Tile getTile(int index){
        return tiles[index];
    }

    /**
     * Shows the tile at the given index.
     * @param index of the tile to show
     * @return if the tile was successfully shown
     */
    public boolean showTile(int index) {
        return tiles[index].show();
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
    private Tile[] getNearbyTiles(int index){
        ArrayList<Tile> nearbyTiles = new ArrayList<>();

        if(index - width - 1 >= 0 && (index - width - 1) / width == (index - width) / width)
            nearbyTiles.add(tiles[index - width - 1]);
        if(index - width >= 0)
            nearbyTiles.add(tiles[index - width]);
        if(index - width + 1 >= 0 && (index - width + 1) / width == (index - width) / width)
            nearbyTiles.add(tiles[index - width + 1]);

        if(index - 1 >= 0 && (index - 1) / width == index / width)
            nearbyTiles.add(tiles[index - 1]);
        if(index + 1 < tiles.length && (index + 1) / width == index / width)
            nearbyTiles.add(tiles[index + 1]);

        if(index + width - 1 < tiles.length && (index + width - 1) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width - 1]);
        if(index + width < tiles.length && (index + width) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width]);
        if(index + width + 1 < tiles.length && (index + width + 1) / width == (index + width) / width)
            nearbyTiles.add(tiles[index + width + 1]);

        Tile[] optimisedNearbyTiles = nearbyTiles.toArray(new Tile[] {});
        return optimisedNearbyTiles;
    }

    public boolean setTileFlagType(int index, FlagType flagType){
        return tiles[index].setFlagType(flagType);
    }
}
