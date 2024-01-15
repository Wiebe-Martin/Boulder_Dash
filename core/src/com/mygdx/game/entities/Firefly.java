
package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

public class Firefly extends Entity {

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    TextureRegion currentFrame;

    float stateTime = 0f;
    int[] glowing = { 90, 91, 92, 93, 94, 95, 96, 97 };

    Direction direction = Direction.LEFT;

    Animation<TextureRegion> glowing_anm;

    public Firefly(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        this.glowing_anm = Animator.getAnimation(glowing);
        this.currentFrame = glowing_anm.getKeyFrame(stateTime, true);
    }

    @Override
    public void update(float delta, ArrayList<Entity> entities) {
        stateTime = stateTime + 1 % glowing.length * delta;
        handleMovement(delta);
        checkKill();

        this.currentFrame = glowing_anm.getKeyFrame(stateTime, true);
        this.entities = entities;
    }

    public void render(SpriteBatch batch) {
        if (isExploding) {
            this.currentFrame = explosion_anm.getKeyFrame(stateTime, false);

            stateTime += Gdx.graphics.getDeltaTime();
            if (explosion_anm.isAnimationFinished(stateTime)) {
                remove();
            }
        }
        batch.draw(currentFrame, x, y);
    }

    private static final float MOVEMENT_COOLDOWN = 0.1f; // Adjust the cooldown time as needed
    private float movementCooldownTimer = 0f;

    public void handleMovement(float delta) {
        if (freezeMovement) {
            return;
        }

        if (movementCooldownTimer > 0f) {
            movementCooldownTimer -= delta;
            return;
        }

        switch (direction) {
            case UP:
                if (!isAir(tileX, tileY + 1) && !isAir(tileX - 1, tileY)) {
                    direction = Direction.RIGHT;
                    break;
                }

                if (!isAir(tileX - 1, tileY) && isAir(tileX, tileY + 1)) {
                    move(tileX, tileY + 1);
                    break;
                }
                if (!isAir(tileX - 1, tileY - 1)) {
                    move(tileX - 1, tileY);
                    direction = Direction.LEFT;
                    break;
                }
                break;

            case DOWN:
                if (!isAir(tileX, tileY - 1) && !isAir(tileX + 1, tileY)) {
                    direction = Direction.LEFT;
                    break;
                }

                if (!isAir(tileX + 1, tileY) && isAir(tileX, tileY - 1)) {
                    move(tileX, tileY - 1);
                    break;
                }
                if (!isAir(tileX + 1, tileY + 1)) {
                    move(tileX + 1, tileY);
                    direction = Direction.RIGHT;
                    break;
                }
                break;

            case LEFT:
                if (!isAir(tileX, tileY - 1) && !isAir(tileX - 1, tileY)) {
                    direction = Direction.UP;
                    break;
                }

                if (!isAir(tileX, tileY - 1) && isAir(tileX - 1, tileY)) {
                    move(tileX - 1, tileY);
                    break;
                }
                if (!isAir(tileX + 1, tileY - 1)) {
                    move(tileX, tileY - 1);
                    direction = Direction.DOWN;
                    break;
                }
                break;

            case RIGHT:
                if (!isAir(tileX, tileY + 1) && !isAir(tileX + 1, tileY)) {
                    direction = Direction.DOWN;
                    break;
                }

                if (!isAir(tileX, tileY + 1) && isAir(tileX + 1, tileY)) {
                    move(tileX + 1, tileY);
                    break;
                }
                if (!isAir(tileX - 1, tileY + 1)) {
                    move(tileX, tileY + 1);
                    direction = Direction.UP;
                    break;
                }
                break;
        }
        movementCooldownTimer = MOVEMENT_COOLDOWN;

    }

    public void checkKill() {

        Player player = getPlayer();
        if (player == null) {
            return;
        }
        int playerX = player.tileX;
        int playerY = player.tileY;

        int[] dx = { 1, -1, 0, 0, };
        int[] dy = { 0, 0, 1, -1, };

        for (int i = 0; i < dx.length; i++) {
            if (playerX == tileX + dx[i] && playerY == tileY + dy[i]) {

                player.kill();
                break;
            }
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
}
