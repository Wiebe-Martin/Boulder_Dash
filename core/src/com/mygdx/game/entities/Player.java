package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;
import com.mygdx.game.utils.PlayerInputProcessor;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {
    PlayerInputProcessor playerInputProcessor;

    TextureRegion currentFrame;

    private int moveSpeed = 5;

    private int cooldown = 0;
    private int coins = 0;

    private int[] walk_left = {40, 41, 42, 43, 44, 45, 46, 47};
    private int[] walk_right = {50, 51, 52, 53, 54, 55, 56, 57};
    private int[] standing = {0};

    Animation<TextureRegion> walk_left_anm;
    Animation<TextureRegion> standing_anm;
    Animation<TextureRegion> walk_right_anm;

    float stateTime = 0;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        walk_left_anm = Animator.getAnimation(walk_left);
        walk_right_anm = Animator.getAnimation(walk_right);
        standing_anm = Animator.getAnimation(standing);

        this.texture = new Texture("textures/entities/player/player.png");
        currentFrame = standing_anm.getKeyFrame(stateTime, true);
        this.sprite = new Sprite(currentFrame);
        this.playerInputProcessor = new PlayerInputProcessor();

        Gdx.input.setInputProcessor(playerInputProcessor);
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {
        handleInput();
        handleAnimation();
        this.entities = entities;
        
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y);
    }

    public void move(int newTileX, int newTileY) {

        Boolean notOutOfBouds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0
                && newTileY < collisionLayer.getHeight();
        Boolean nocollision = collisionLayer.getCell(newTileX, newTileY) == null && !isStone(newTileX, newTileY);

        if (notOutOfBouds && nocollision) {
            this.tileX = newTileX;
            this.tileY = newTileY;

            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        }

        handelCollison();
    }

    public void handelCollison() {
        boolean collision = collisionLayer.getCell(tileX, tileY) == null;
        if (collision) {
            // Modify the map data (e.g., set the tile to null or update properties)
            dirtLayer.setCell(tileX, tileY, null);
        }
        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof Coin && entity.getTileX() == tileX && entity.getTileY() == tileY) {
                coins++;
                entity.remove();
            }
        }
    }

    public void handleInput() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }
        if (playerInputProcessor.isLeft()) {
            move(tileX - 1, tileY);
        } else if (playerInputProcessor.isRight()) {
            move(tileX + 1, tileY);
        } else if (playerInputProcessor.isUp()) {
            move(tileX, tileY + 1);
        } else if (playerInputProcessor.isDown()) {
            move(tileX, tileY - 1);
        }
        if (playerInputProcessor.isLeft() || playerInputProcessor.isRight() || playerInputProcessor.isUp()
                || playerInputProcessor.isDown()) {
            cooldown = moveSpeed;
        }
    }

    public void handleAnimation() {
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        if (playerInputProcessor.isLeft()) {
            currentFrame = walk_left_anm.getKeyFrame(stateTime, true);
        } else if (playerInputProcessor.isRight()) {
            currentFrame = walk_right_anm.getKeyFrame(stateTime, true);
        } else{
            currentFrame = standing_anm.getKeyFrame(stateTime, true);
        }
    }

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return "Player at (" + tileX + ", " + tileY + ")";
    }
}
