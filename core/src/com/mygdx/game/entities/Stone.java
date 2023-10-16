package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Stone extends Entity{

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        //this.texture = new Texture("textures/stone.png");
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
