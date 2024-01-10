package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.rendering.Animator;

import java.util.ArrayList;

public class Entity {
    protected TextureRegion currentFrame;

    protected int[] def = { 9 };
    protected int[] explosion = { 1, 2, 3, 3, 2, 1 };

    protected Animation<TextureRegion> def_anm;
    protected Animation<TextureRegion> explosion_anm;

    protected float x, y;

    protected int tileX, tileY;

    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    protected TiledMapTileLayer dirtLayer;

    protected float moveSpeed;
    protected float stateTime = 0;

    protected ArrayList<Entity> entities = new ArrayList<Entity>();
    protected boolean remove = false;
    protected boolean isExploding = false;

    public Entity(TiledMap map, int startX, int startY) {
        this.map = map;
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision");
        this.dirtLayer = (TiledMapTileLayer) map.getLayers().get("dirt");
        this.tileX = startX;
        this.tileY = startY;

        this.x = tileX * collisionLayer.getTileWidth();
        this.y = tileY * collisionLayer.getTileHeight();

        this.def_anm = Animator.getAnimation(def);
        this.currentFrame = def_anm.getKeyFrame(stateTime, true);
        this.explosion_anm = Animator.getAnimation(explosion);
    }

    public void render(SpriteBatch batch) {

        if (isExploding) {
            this.currentFrame = explosion_anm.getKeyFrame(stateTime, false);
            stateTime += Gdx.graphics.getDeltaTime() * 0.5f;
            if (explosion_anm.isAnimationFinished(stateTime)) {
                remove();
            }
        }

        batch.draw(currentFrame, x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void remove() {
        remove = true;
    }

    public boolean isRemove() {
        return remove;
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {
        stateTime += Gdx.graphics.getDeltaTime();

        this.currentFrame = def_anm.getKeyFrame(stateTime, true);
        this.entities = entities;
    }

    protected boolean isAir(int x, int y) {
        return collisionLayer.getCell(x, y) == null && !isDirt(x, y) && !isStone(x, y) && !isPlayer(x, y);
    }

    protected boolean isPlayer(int tileX, int tileY) {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (entity.getTileX() == tileX && entity.getTileY() == tileY) {
                    return true;
                }
            }
        }
        return false;

    }

    protected boolean isDirt(int x, int y) {
        return dirtLayer.getCell(x, y) != null;
    }

    protected boolean isStone(int tileX, int tileY) {
        for (Entity entity : entities) {
            if (entity instanceof Stone) {
                if (entity.getTileX() == tileX && entity.getTileY() == tileY) {
                    return true;
                }
            }
        }
        return false;
    }

    public void explode() {
        isExploding = true;
    }

    protected Entity getEntity(int tileX, int tileY) {
        for (Entity entity : entities) {
            if (entity.getTileX() == tileX && entity.getTileY() == tileY) {
                return entity;
            }
        }

        return null;
    }

    public void handleCollison() {
        System.out.println(getTileX() + "/" + getTileY());
    };
}
