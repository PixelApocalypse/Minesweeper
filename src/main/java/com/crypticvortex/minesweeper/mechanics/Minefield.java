package com.crypticvortex.minesweeper.mechanics;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stores data on mine positions, etc.
 */
public class Minefield {
    private Tile[] tiles;
    private Difficulty diff;
    private int width, height;
    private int seed;

    private final float MINE_PERCENT = 20f;

    public Minefield(int size, Difficulty diff) {
        this.seed = new Random().nextInt();
        this.width = size;
        this.height = size;
        this.diff = diff;
        this.tiles = new Tile[width * height];
    }

    public void populate() {
        ArrayList<Integer> minesCoordonate = getMinesCoordinate();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(minesCoordonate.contains(i));
        }
    }

    private ArrayList<Integer> getMinesCoordinate(){
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
        return tiles[index];
    }

    public boolean showTile(int index) {
        Tile tile = tiles[index];

        if(tile.getFlagType() != FlagType.INVALID)
            return false;

        if(tile.isMine()){
            //...
        }
        //if()
        return false;
    }

    private Tile[] getNearbyTiles(int index){
        Tile[] nearbyTiles = new Tile[8];
        int y = index / width;

        if(!(y - width < 0)) {
            if((index - 1)/width == y - 1)
                nearbyTiles[0] = tiles[index - width - 1];
            nearbyTiles[1] = tiles[index - width];
            if((index + 1)/width == y - 1)
                nearbyTiles[2] = tiles[index - width + 1];
        }

        if((index - 1) / width == y)
            nearbyTiles[3] = tiles[index - 1];
        if((index + 1) / width == y)
            nearbyTiles[4] = tiles[index + 1];

        if(!(y + width >= tiles.length)){
            if((index - 1)/width == y + 1)
                nearbyTiles[0] = tiles[index + width - 1];
            nearbyTiles[1] = tiles[index + width];
            if((index + 1)/width == y + 1)
                nearbyTiles[2] = tiles[index + width + 1];
        }

        int nearbyTilesAmount = 0;
        for(Tile tile : nearbyTiles){
            if(tile != null)
                ++nearbyTilesAmount;
        }
        return nearbyTiles;
    }


}
