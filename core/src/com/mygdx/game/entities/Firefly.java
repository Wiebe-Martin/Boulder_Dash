package com.mygdx.game.entities;

import java.util.ArrayList;

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
    int[] glowing = {90, 91 , 92, 93, 94, 95, 96, 97};

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

        this.currentFrame = glowing_anm.getKeyFrame(stateTime, true);
        this.entities = entities;
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y);
    }


    private static final float MOVEMENT_COOLDOWN = 0.5f; // Adjust the cooldown time as needed
    private float movementCooldownTimer = 0f;

    public void handleMovement(float delta) {
        if (movementCooldownTimer <= 0f) {
            System.out.println(direction);
            switch (direction) {
                case UP:
                    if(!isAir(tileX, tileY + 1) && !isAir(tileX - 1, tileY)) {
                        direction = Direction.RIGHT;
                        break;
                    }
        
                    if(!isAir(tileX - 1, tileY) && isAir(tileX, tileY + 1)) {
                        move(tileX, tileY + 1);
                        break;
                    }   
                    if(!isAir(tileX - 1, tileY - 1)) {
                        move(tileX - 1, tileY);
                        break;
                    }
                    break;
                    case DOWN:
                        if (isAir(tileX, tileY - 1)) {
                            move(tileX, tileY - 1);
                        } else {
                            direction = Direction.LEFT;
                        }   
                        break;
                    break;
                case LEFT:
                    if (isAir(tileX - 1, tileY)) {
                        move(tileX - 1, tileY);
                    } else {
                        direction = Direction.UP;
                    }
                    break;
                case RIGHT:
                    if (isAir(tileX + 1, tileY)) {
                        move(tileX + 1, tileY);
                    } else {
                        direction = Direction.DOWN;
                    }
                    break;
            }
            movementCooldownTimer = MOVEMENT_COOLDOWN;
        } else {
            movementCooldownTimer -= delta; // Assuming delta is the time since the last update
        }
    }

    public void changeDirection() {
        switch(direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;

        }
    }

    public void handleMovement(Direction direction) {

    }

    public void moveUp() {
        if(!isAir(tileX, tileY + 1) && !isAir(tileX - 1, tileY)) {
            direction = Direction.RIGHT;
            break;
        }

        if(!isAir(tileX - 1, tileY) && isAir(tileX, tileY + 1)) {
            move(tileX, tileY + 1);
            break;
        }
        if(!isAir(tileX - 1, tileY - 1)) {
            move(tileX - 1, tileY);
            break;
        }
    }

    public void checkKill() {
        Player player = getPlayer();
        switch (direction) {
            case UP:
                if(player.tileX == tileX && player.tileY == tileY + 1) {
                    player.kill();
                }
            case DOWN:
                if(player.tileX == tileX && player.tileY == tileY - 1) {
                    player.kill();
                }
            case LEFT:
                if(player.tileX == tileX - 1 && player.tileY == tileY) {
                    player.kill();
                }
            case RIGHT:
                if(player.tileX == tileX + 1 && player.tileY == tileY) {
                    player.kill();
        }    }
        
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
