package com.mygdx.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Explosion extends Entity {
    public Explosion(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        explode();
    }

}
