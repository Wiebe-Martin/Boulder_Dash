package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Stone extends Entity{

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/stone.png");
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update(float deltaTime) {
        handelCollison();
    }

    public void move(int newTileX, int newTileY) {
        Boolean outOfBouds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0 && newTileY < collisionLayer.getHeight();
        
        if (outOfBouds) {
            this.tileX = newTileX;
            this.tileY = newTileY;
        
            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        }
    }

    public void handelCollison() {
        System.out.println("check");
        boolean isFalling = collisionLayer.getCell(tileX, tileY - 1) == null && dirtLayer.getCell(tileX, tileY - 1) == null;
        if(isFalling) {
            move(tileX, tileY - 1);
        }
    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
