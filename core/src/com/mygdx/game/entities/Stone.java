package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;

public class Stone extends Entity {
    private int moveSpeedDown = 6;
    private int moveSpeedSide = 8;

    private int cooldown = 0;

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/stone.png");
        this.sprite = new Sprite(texture);
    }

    @Override
    public void update(float deltaTime, ArrayList<Entity> entities) {
        this.entities = entities;
        handleCollision();

    }

    public void handleCollision() {
        boolean canFallLeft = isAir(tileX - 1, tileY) && isAir(tileX - 1, tileY - 1) && isStone(tileX, tileY - 1);
        boolean canFallRight = isAir(tileX + 1, tileY) && isAir(tileX + 1, tileY - 1) && isStone(tileX, tileY - 1);
        boolean canFallDown = isAir(tileX, tileY - 1) && !isStone(tileX, tileY - 1);
        // boolean canFall = canFallLeft || canFallRight || canFallDown;

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (canFallDown) {
            move(tileX, tileY - 1);
            cooldown = moveSpeedDown;
        } else if (canFallLeft) {
            move(tileX - 1, tileY - 1);
            cooldown = moveSpeedSide;
        } else if (canFallRight) {
            move(tileX + 1, tileY - 1);
            cooldown = moveSpeedSide;
        }
        cooldown--;

    }

    public void move(int newTileX, int newTileY) {
        boolean outOfBounds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0
                && newTileY < collisionLayer.getHeight();

        if (outOfBounds) {
            this.tileX = newTileX;
            this.tileY = newTileY;

            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        }

    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
