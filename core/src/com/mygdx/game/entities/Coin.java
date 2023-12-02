package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;


public class Coin extends Entity{

    public Coin(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/coin/coin1.png");
        this.sprite = new Sprite(texture);
    }

   

    @Override
    public String toString() {
        return "Coin at (" + tileX + ", " + tileY + ")";
    }
}
