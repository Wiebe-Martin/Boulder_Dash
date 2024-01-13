package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

import java.util.ArrayList;

public class Stone extends Entity {
    protected int[] texture = { 70 };

    Animation<TextureRegion> texture_anm;


    private int startCooldown = 20;
    private int moveSpeedDown = 20;
    private int moveSpeedSide = 25;

    private int cooldown;
    private int startCooldownDownCounter = startCooldown;

    private boolean falling;

    public Stone(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.cooldown = moveSpeedDown;

        this.texture_anm = Animator.getAnimation(texture);
        this.currentFrame = texture_anm.getKeyFrame(1, false);

    }

    @Override
    public void update(float deltaTime, ArrayList<Entity> entities) {
        this.entities = entities;
        handleCollision();
    }

    public void handleCollision() {

        if (freezeMovement || isExploding) {
            return;
        }


        boolean canFallLeft = isAir(tileX - 1, tileY) && isAir(tileX - 1, tileY - 1) && isStone(tileX, tileY - 1);
        boolean canFallRight = isAir(tileX + 1, tileY) && isAir(tileX + 1, tileY - 1) && isStone(tileX, tileY - 1);
        boolean canFallDown = isAir(tileX, tileY - 1);
        boolean canFall = canFallLeft || canFallRight || canFallDown;

        if (startCooldownDownCounter > 0 && canFall && !falling) {
            startCooldownDownCounter--;
            return;
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!isFreeFall(tileX, tileY - 1)) {
            falling = false;
            startCooldownDownCounter = startCooldown;

        }

        if (isPlayer(tileX, tileY - 1) && falling && getPlayer().dead == false) {
            falling = true;
            getPlayer().kill();
            return;
        }

        if (canFallDown) {
            move(tileX, tileY - 1);
            falling = true;
            cooldown = moveSpeedDown;
            return;
        } else if (canFallLeft) {
            move(tileX - 1, tileY - 1);
            falling = true;
            cooldown = moveSpeedSide;
            return;
        } else if (canFallRight) {
            move(tileX + 1, tileY - 1);
            falling = true;
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

    public boolean isFalling() {
        return falling;
    }

    @Override
    public String toString() {
        return "Stone at (" + tileX + ", " + tileY + ")";
    }
}
