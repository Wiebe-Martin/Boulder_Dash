package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private SpriteBatch batch;
    private float x;
    private float y;

    public void update(float deltaTime) {
        // Implement player-specific update logic here
    }

    @Override
    public void render(SpriteBatch batch) {
        // Render the player sprite
        batch.draw(playerTexture, x, y, width, height);
    }
}
