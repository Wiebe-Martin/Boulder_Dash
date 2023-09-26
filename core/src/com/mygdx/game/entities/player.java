package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player extends Entity{
    private float moveSpeed = 2.0f;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/player/player_front.png"); // Adjust the player texture
    }

    public void update(float deltaTime) {
        // Implement player-specific update logic here
        
    }

    
}
