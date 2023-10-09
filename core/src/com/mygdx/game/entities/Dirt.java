package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Dirt extends Entity{
    public Dirt(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/dirt.png");
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public String toString() {
        return "Dirt at (" + tileX + ", " + tileY + ")";
    }
}
