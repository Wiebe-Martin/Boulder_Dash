package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {
    private Sprite sprite;
    private float x, y;
    private int tileX, tileY;
    private TiledMap map;
    private TiledMapTileLayer collisionLayer;
    private float moveSpeed = 2.0f;

    public Player(TiledMap map) {
        this.map = map;
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision"); // Adjust layer name
        this.sprite = new Sprite(new Texture("sprites/sprites.png")); // Adjust the player texture

        // Initialize player's position (tile and pixel coordinates)
        this.tileX = 0;
        this.tileY = 0;
        this.x = tileX * collisionLayer.getTileWidth();
        this.y = tileY * collisionLayer.getTileHeight();
    }

    public void update(float deltaTime) {
        // Implement player-specific update logic here
    }

}
