package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Player extends Entity{
    private float moveSpeed = 2.0f;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/player/player_front.png");
        this.sprite = new Sprite(texture); // Adjust the player texture
    }

    public void update(float deltaTime) {
        // Implement player-specific update logic here
        handleInput();
    }

    public void handleInput() {
        float speed = moveSpeed;
    
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed;
        }
    }
    
}
