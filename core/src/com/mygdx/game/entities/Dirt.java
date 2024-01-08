package com.mygdx.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Dirt extends Entity{
    public Dirt(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
    }



    @Override
    public String toString() {
        return "Dirt at (" + tileX + ", " + tileY + ")";
    }
}
