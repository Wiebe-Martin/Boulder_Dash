package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

public class Coin extends Entity {
    protected int[] texture = { 100, 101, 102, 103, 104, 105, 106, 107 };

    Animation<TextureRegion> texture_anm;

    public Coin(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        this.texture_anm = Animator.getAnimation(texture);

        this.currentFrame = texture_anm.getKeyFrame(stateTime, true);
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {

        if (freezeMovement || isExploding) {
            return;
        }

        stateTime = stateTime + 1 % texture.length * deltaTime;

        this.currentFrame = texture_anm.getKeyFrame(stateTime, true);
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "Coin at (" + tileX + ", " + tileY + ")";
    }
}
