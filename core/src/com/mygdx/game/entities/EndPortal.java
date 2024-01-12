package com.mygdx.game.entities;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;

public class EndPortal extends Entity {

    protected int[] texture = { 62 };

    Animation<TextureRegion> texture_anm;

    public EndPortal(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        this.texture_anm = Animator.getAnimation(texture);
        this.currentFrame = texture_anm.getKeyFrame(stateTime, false);

    }

}
