package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.AnimationPath;
import com.mygdx.game.utils.AnimationPoint;

import java.util.ArrayList;

public class Stone extends Entity {
    private int moveSpeedDown = 60;
    private int moveSpeedSide = 120;

    private int cooldown = 0;
    private int fallingduration;
    private ArrayList<Entity> entities;

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
        boolean canFallLeft = isAir(tileX - 1, tileY) && isAir(tileX - 1, tileY - 1) && !isStone(tileX, tileY - 1);
        boolean canFallRight = isAir(tileX + 1, tileY) && isAir(tileX + 1, tileY - 1) && !isStone(tileX, tileY - 1);
        boolean canFallDown = isAir(tileX, tileY - 1) && !isStone(tileX, tileY - 1);
        boolean canFall = canFallLeft || canFallRight || canFallDown;

        if (!canFall && cooldown <= 0) {
            cooldown = 0;

        }

        if (canFallLeft) {
            move(tileX - 1, tileY - 1);
        } else if (canFallRight) {
            move(tileX + 1, tileY - 1);
        } else if (canFallDown) {
            move(tileX, tileY - 1);
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

    private boolean isAir(int x, int y) {
        return collisionLayer.getCell(x, y) == null && dirtLayer.getCell(x, y) == null;
    }

    private boolean isStone(int x, int y) {
        for (Entity entity : entities) {
            if (entity instanceof Stone) {
                System.out.println("Stone found");
                if (entity.getTileX() == x && entity.getTileY() == y) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
