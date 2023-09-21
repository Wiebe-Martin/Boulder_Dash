package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
    public Tile(int y, int x, Texture texture) {
        this.y = y;
        this.x = x;
        this.texture = texture;
    }

    public Tile(int y, int x, Tile tileBelow, Tile tileAbove, Tile tileLeft, Tile tileRight, Texture texture) {
        this.y = y;
        this.x = x;
        this.tileBelow = tileBelow;
        this.tileAbove = tileAbove;
        this.tileLeft = tileLeft;
        this.tileRight = tileRight;
        this.texture = texture;
    }

    public int y;
    public int x;
    public Tile tileBelow;
    public Tile tileAbove;
    public Tile tileLeft;
    public Tile tileRight;

    public Texture texture;


}
