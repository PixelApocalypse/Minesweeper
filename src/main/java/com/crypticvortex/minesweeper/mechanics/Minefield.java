package com.crypticvortex.minesweeper.mechanics;

import java.lang.reflect.Array;
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
        ArrayList<Integer> minesCoordonate = getMinesCoordonate();
        for(int i = 0; i < width * height; ++i){
            tiles[i] = new Tile(minesCoordonate.contains(i));
        }
    }

    private ArrayList<Integer> getMinesCoordonate(){
        Random random = new Random(this.seed);
        int nbOfMines = Math.round(tiles.length / (MINE_PERCENT + diff.getAmount()));
        ArrayList<Integer> minesCoordonate = new ArrayList<>(nbOfMines);

        for(int i = 0; i < nbOfMines; ++i) {
            int mineLocation = random.nextInt();
            if (minesCoordonate.contains(mineLocation)) {
                --i;
                continue;
            }
            minesCoordonate.add(mineLocation);
        }
        return minesCoordonate;
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


}
