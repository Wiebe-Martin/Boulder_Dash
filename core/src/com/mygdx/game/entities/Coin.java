package com.mygdx.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class Coin extends Entity{

    public Coin(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public String toString() {
        return "Coin";
    }
}
