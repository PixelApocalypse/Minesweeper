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
        return new Tile(tiles[index]);
    }

    public boolean showTile(int index) {
        return tiles[index].show();
    }

    public int getNearbyMines(int index){
        return getNearbyTiles(index).length;
    }

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
        Tile[] optimisedNearbyTiles = (Tile[]) nearbyTiles.toArray();

        return optimisedNearbyTiles;
    }


}
