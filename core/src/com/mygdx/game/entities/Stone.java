package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Stone extends Entity{
    private int moveSpeed = 12;        
    private int cooldown = 0;

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/stone.png");
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update(float deltaTime) {
        handleCollision();
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

    public void handleCollision() {
        boolean canFall = isAir(tileX, tileY - 1) && isAir(tileX, tileY - 1);
        
        if (!canFall) {
            cooldown = moveSpeed;
            return;
        }
    
        if (cooldown > 0) {
            cooldown--;
        } else {
            move(tileX, tileY - 1);
        }
    }
    
    private boolean isAir(int x, int y) {
        return collisionLayer.getCell(x, y) == null && dirtLayer.getCell(x, y) == null;
    }
    

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
