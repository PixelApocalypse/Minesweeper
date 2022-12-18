package com.crypticvortex.minesweeper.mechanics;

/**
 * Possible image scale values.
 *
 * @author Jatboy
 */
public enum GameScale {
    DEFAULT("x1/"),
    TIMES_2("x2/"),
    ;

    private String scale;

    GameScale(String scale) {
        this.scale = scale;
    }

    public String getScale() {
        return scale;
    }

}
