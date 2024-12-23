package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;
import com.mygdx.game.utils.PlayerInputProcessor;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {

    PlayerInputProcessor playerInputProcessor;

    private int moveSpeed = 10;
    private int waitStandingStillTime = 20;
    private int stoneMoveThreshold = 80;
    private int[] walk_left = { 40, 41, 42, 43, 44, 45, 46, 47 };
    private int[] walk_right = { 50, 51, 52, 53, 54, 55, 56, 57 };
    private int[] standing = { 10, 11, 12, 13, 14, 15, 16, 17,
            20, 21, 22, 23, 24, 25, 26, 27,
            30, 31, 32, 33, 34, 35, 36, 37, };

    public boolean dead = false;
    private boolean isBase = false;

    private int cooldown = 0;
    private int coins = 0;
    private int waitStandingStillCounter = 0;
    private int stoneMoveThresholdCounter = 0;

    Animation<TextureRegion> walk_left_anm;
    Animation<TextureRegion> standing_anm;
    Animation<TextureRegion> walk_right_anm;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        walk_left_anm = Animator.getAnimation(walk_left);
        walk_right_anm = Animator.getAnimation(walk_right);
        standing_anm = Animator.getAnimation(standing);

        currentFrame = standing_anm.getKeyFrame(stateTime, true);

        this.playerInputProcessor = new PlayerInputProcessor();

        Gdx.input.setInputProcessor(playerInputProcessor);
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {
        this.entities = entities;

        if (dead) {
            return;
        }

        handleInput();
        handleAnimation();
        handleStoneMoving();
        checkIfBase();
    }

    public void move(int newTileX, int newTileY) {
        Boolean notOutOfBouds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0
                && newTileY < collisionLayer.getHeight();
        Boolean nocollision = collisionLayer.getCell(newTileX, newTileY) == null;
        Boolean stoneCollison = isStone(newTileX, newTileY);

        if (notOutOfBouds && nocollision && !stoneCollison) {
            this.tileX = newTileX;
            this.tileY = newTileY;

            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        }

        handleCollison();
    }

    public void kill() {
        dead = true;
        for (Entity entity : entities) {
            entity.freezeMovement();
        }

        explode();
    }

    public Boolean isBase() {
        return isBase;
    }

    public void handleCollison() {
        boolean collision = collisionLayer.getCell(tileX, tileY) == null;
        if (collision) {
            // Modify the map data (e.g., set the tile to null or update properties)
            dirtLayer.setCell(tileX, tileY, null);
        }

        Iterator<Entity> iterator1 = entities.iterator();
        while (iterator1.hasNext()) {
            Entity entity = iterator1.next();
            if (entity instanceof Coin && entity.getTileX() == tileX && entity.getTileY() == tileY) {               
                entity.remove();
                coins++;
            }
        }
    }

    public void handleInput() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (playerInputProcessor.isLeft()) {
            if (playerInputProcessor.isShift()) {
                removeDirt(tileX - 1, tileY);
            } else {
                move(tileX - 1, tileY);
            }
        } else if (playerInputProcessor.isRight()) {
            if (playerInputProcessor.isShift()) {
                removeDirt(tileX + 1, tileY);
            } else {
                move(tileX + 1, tileY);
            }
        } else if (playerInputProcessor.isUp()) {
            if (playerInputProcessor.isShift()) {
                removeDirt(tileX, tileY + 1);
            } else {
                move(tileX, tileY + 1);
            }
        } else if (playerInputProcessor.isDown()) {
            if (playerInputProcessor.isShift()) {
                removeDirt(tileX, tileY - 1);
            } else {
                move(tileX, tileY - 1);
            }
        }

        cooldown = moveSpeed;
    }

    private void removeDirt(int tileX, int tileY) {
        if (dirtLayer.getCell(tileX, tileY) != null) {
            dirtLayer.setCell(tileX, tileY, null);
        }
    }

    public void handleAnimation() {
        // stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation
        // time

        if (playerInputProcessor.isUp() || playerInputProcessor.isDown() || playerInputProcessor.isLeft()
                || playerInputProcessor.isRight()) {
            waitStandingStillCounter = 0;
        }
        if (playerInputProcessor.isLeft()) {
            currentFrame = walk_left_anm.getKeyFrame(stateTime, true);
            stateTime = stateTime + 1 % walk_left.length * Gdx.graphics.getDeltaTime();
            return;
        }
        if (playerInputProcessor.isRight()) {
            currentFrame = walk_right_anm.getKeyFrame(stateTime, true);
            stateTime = stateTime + 1 % walk_right.length * Gdx.graphics.getDeltaTime();
            return;
        }

        if (waitStandingStillCounter > waitStandingStillTime) {
            currentFrame = standing_anm.getKeyFrame(stateTime, true);
            stateTime = stateTime + 1 % standing.length * Gdx.graphics.getDeltaTime() * 0.5f;
            return;
        }
        currentFrame = standing_anm.getKeyFrame(3, true);

        waitStandingStillCounter++;

    }

    private void handleStoneMoving() {
        if (playerInputProcessor.isLeft() || playerInputProcessor.isRight()) {
            stoneMoveThresholdCounter++;

        } else {
            stoneMoveThresholdCounter = 0;
        }
        if (stoneMoveThresholdCounter > stoneMoveThreshold) {
            stoneMoveThresholdCounter = 0;
            moveConnectingStone();

        }
    }

    private void moveConnectingStone() {
        if (playerInputProcessor.isLeft()) {
            for (Entity entity : entities) {
                if (entity instanceof Stone && entity.getTileX() == tileX - 1 && entity.getTileY() == tileY) {
                    ((Stone) entity).pushLeft();
                }
            }
            move(tileX - 1, tileY);
        }
        if (playerInputProcessor.isRight()) {
            for (Entity entity : entities) {
                if (entity instanceof Stone && entity.getTileX() == tileX + 1 && entity.getTileY() == tileY) {
                    ((Stone) entity).pushRight();
                }
            }
            move(tileX + 1, tileY);
        }

    }

    public int getCoins() {
        return coins;
    }

    public void checkIfBase() {
        if(isEndPortal(tileX, tileY)) {
            isBase = true;
        }
    }

    @Override
    public String toString() {
        return "Player at (" + tileX + ", " + tileY + ")";
    }
}
