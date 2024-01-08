package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

public class Coin extends Entity {

    TextureRegion currentFrame;
    private int[] coin = { 100, 101, 102, 103, 104, 105, 106, 107 };
    Animation<TextureRegion> coin_anm;
    float stateTime = 0;

    public Coin(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture = new Texture("textures/entities/coin/coin1.png");
        this.sprite = new Sprite(texture);
        coin_anm = Animator.getAnimation(coin);
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += 1 % coin.length * Gdx.graphics.getDeltaTime() * 0.5;
        currentFrame = coin_anm.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x, y);
    }

    @Override
    public String toString() {
        return "Coin at (" + tileX + ", " + tileY + ")";
    }
}
