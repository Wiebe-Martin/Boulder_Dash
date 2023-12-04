package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.utils.PlayerInputProcessor;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity {
    PlayerInputProcessor playerInputProcessor;

    private int moveSpeed = 3;

    private int cooldown = 0;
    private int coins = 0;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/player/player.png");
        this.sprite = new Sprite(texture);
        this.playerInputProcessor = new PlayerInputProcessor();

        Gdx.input.setInputProcessor(playerInputProcessor);
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {
        handleInput();
        this.entities = entities;
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
                iterator.remove();
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

    public int getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return "Player at (" + tileX + ", " + tileY + ")";
    }
}
