package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

import java.util.ArrayList;

public class Stone extends Entity {
    protected int[] texture = {70};

    Animation<TextureRegion> texture_anm;

    private int moveSpeedDown = 6;
    private int moveSpeedSide = 8;

    private int cooldown = 0;

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        this.texture_anm = Animator.getAnimation(texture);        
        this.currentFrame = texture_anm.getKeyFrame(stateTime, false);
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

    public void pushLeft() {
        boolean canPush = isAir(tileX - 1, tileY) && !isStone(tileX - 1, tileY);
        if (canPush) {
            move(tileX - 1, tileY);
        }
    }

    public void pushRight() {
        boolean canPush = isAir(tileX + 1, tileY) && !isStone(tileX + 1, tileY);
        if (canPush) {
            move(tileX + 1, tileY);
        }
    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
