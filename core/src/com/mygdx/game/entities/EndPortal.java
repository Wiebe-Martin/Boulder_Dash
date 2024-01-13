package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.rendering.Animator;
import java.util.ArrayList;

public class EndPortal extends Entity {
    TextureRegion currentFrame;

    int[] texture = {61};
    boolean isActivated = false;

    Animation<TextureRegion> texture_anm;

    public EndPortal(TiledMap map, int startX, int startY) {
        super(map, startX, startY);

        this.texture_anm = Animator.getAnimation(texture);
        this.currentFrame = texture_anm.getKeyFrame(stateTime, false);
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y);
    }

    public void update(float delta) {
        Player player = getPlayer();

       if (!isActivated && player.getTileX() == getTileX() && player.getTileY() == getTileY()) {
              isActivated = true;
              player.win();
         }
    
    }
}
