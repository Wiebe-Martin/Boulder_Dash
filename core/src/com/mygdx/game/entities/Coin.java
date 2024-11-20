package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

public class Coin extends Entity {
    protected int[] texture = { 100, 101, 102, 103, 104, 105, 106, 107 };

    Animation<TextureRegion> texture_anm;

    private int startCooldown = 20;
    private int moveSpeedDown = 20;
    private int moveSpeedSide = 25;

    private int cooldown;
    private int startCooldownDownCounter = startCooldown;

    public Coin(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        this.texture_anm = Animator.getAnimation(texture);

        this.currentFrame = texture_anm.getKeyFrame(stateTime, true);
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {

        if (freezeMovement || isExploding) {
            return;
        }

        stateTime = stateTime + 1 % texture.length * deltaTime;

        this.currentFrame = texture_anm.getKeyFrame(stateTime, true);
        this.entities = entities;
    }

    
    public void handleCollision() {

        if (freezeMovement || isExploding) {
            return;
        }


        boolean canFallLeft = isAir(tileX - 1, tileY) && isAir(tileX - 1, tileY - 1) && isCoin(tileX, tileY - 1) &&isWall(tileX, tileY - 1) ;
        boolean canFallRight = isAir(tileX + 1, tileY) && isAir(tileX + 1, tileY - 1) && isCoin(tileX, tileY - 1) && isWall(tileX, tileY - 1);
        boolean canFallDown = isAir(tileX, tileY - 1);
        boolean canFall = canFallLeft || canFallRight || canFallDown;

        if (startCooldownDownCounter > 0 && canFall ) {
            startCooldownDownCounter--;
            return;
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!isFreeFall(tileX, tileY - 1)) {
            
            startCooldownDownCounter = startCooldown;

        }

        

        if (canFallDown) {
            move(tileX, tileY - 1);
            
            cooldown = moveSpeedDown;
            return;
        } else if (canFallLeft) {
            move(tileX - 1, tileY - 1);
            
            cooldown = moveSpeedSide;
            return;
        } else if (canFallRight) {
            move(tileX + 1, tileY - 1);
            
            cooldown = moveSpeedSide;
            return;
        }

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
        return "Coin at (" + tileX + ", " + tileY + ")";
    }
}
