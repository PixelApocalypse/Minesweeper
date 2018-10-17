package com.crypticvortex.minesweeper.mechanics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Stores data on mine positions, etc.
 */
public class Minefield {
    private static final java.util.Arrays Arrays = ;
    private Tile[] tiles;
    private Difficulty diff;
    private int width, height;
    private int seed;

    private final float MINE_PERCENT = 20f;

    /**
     *Create a new minefield with the given parameters.
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
     * Create a new minefield with the given parameters.
     * @param size the width and height of the minefield
     * @param diff the amount of mines depends on the difficulty
     */
    public Minefield(int size, Difficulty diff) {
        this(size, size, diff);
    }

    /**
     * Generates tiles and put them in the minefields.
     */
    public void populate() {
        ArrayList<Integer> minesCoordonate = getMinesIndex();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(minesCoordonate.contains(i));
        }
    }

    /**
     * Gets and returns the index of all mines.
     * @return List of mines' index
     */
    private ArrayList<Integer> getMinesIndex(){
        Random random = new Random(this.seed);
        int nbOfMines = Math.round(tiles.length * (MINE_PERCENT + diff.getAmount()) / 100);
        ArrayList<Integer> minesCoordinate = new ArrayList<>(nbOfMines);

        for(int i = 0; i < nbOfMines; ++i) {
            int mineLocation = random.nextInt(tiles.length);
            if (minesCoordinate.contains(mineLocation)) {
                --i;
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
        return new Tile(tiles[index]);
    }

    /**
     * Shows the tile at the given index.
     * @param index index of the tile to show
     * @return if the tile was successfully shown
     */
    public boolean showTile(int index) {
        return tiles[index].show();
    }

    /**
     * Gets and returns the amount of mines around the tile at the given index.
     * @param index index of the tile around which to look
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
     * Verifies if the game was won.
     * @return is the game finished
     */
    public boolean gameWon(){
        for(int i = 0; i < tiles.length; ++i){
            if(!tiles[i].isShown() && !tiles[i].isMine())
                return false;
        }
        return true;
    }

    public int[] getNearbyTilesIndex(int index){
        Tile[] nearbyTiles = getNearbyTiles(index);
        int[] nearbyTilesIndex = new int[nearbyTiles.length];
        for(int i = 0; i < nearbyTilesIndex.length; ++i){
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
        int y = index / width;

        if(!(y - width < 0)) {
            if((index - 1)/width == y - 1)
                nearbyTiles.add(tiles[index - width - 1]);
            nearbyTiles.add(tiles[index - width]);
            if((index + 1)/width == y - 1)
                nearbyTiles.add(tiles[index - width + 1]);
        }

        if((index - 1) / width == y)
            nearbyTiles.add(tiles[index - 1]);
        if((index + 1) / width == y)
            nearbyTiles.add(tiles[index + 1]);

        if(!(y + width >= tiles.length)){
            if((index - 1)/width == y + 1)
                nearbyTiles.add(tiles[index + width - 1]);
            nearbyTiles.add(tiles[index + width]);
            if((index + 1)/width == y + 1)
                nearbyTiles.add(tiles[index + width + 1]);
        }

        nearbyTiles.removeAll(null);

        Tile[] optimisedNearbyTiles = nearbyTiles.toArray(new Tile[] {});

        return optimisedNearbyTiles;
    }
}
