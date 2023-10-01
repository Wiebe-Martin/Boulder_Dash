package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Player extends Entity{
    //private float moveSpeed = 2.0f;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/player/player_front.png");
        this.sprite = new Sprite(texture); // Adjust the player texture
    }

    public void update(float deltaTime) {
        handleInput();
    }

    public void move(int newTileX, int newTileY) {
        // Check if the new tile coordinates are within the map boundaries
        Boolean outOfBouds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0 && newTileY < collisionLayer.getHeight();
        // Check if the new tile is empty
        Boolean collision = collisionLayer.getCell(newTileX, newTileY) == null;
        if (outOfBouds && collision) { // Check if the new tile is empty and within the map boundaries
    
            this.tileX = newTileX;
            this.tileY = newTileY;
        
            // Update the player's actual position based on the new tile position
            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        }

    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.move(tileX - 1, tileY); // Move left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.move(tileX + 1, tileY); // Move right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.move(tileX, tileY + 1); // Move up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.move(tileX, tileY - 1); // Move down
        }
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public String toString() {
        return "Player at (" + tileX + ", " + tileY + ")";
    }
}
