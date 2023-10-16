package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.GameTextures;

public class Entity {
    protected Sprite sprite;
    protected Texture texture;
    protected float x, y;
    protected int tileX, tileY;
    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    protected float moveSpeed;

    public Entity(TiledMap map, int startX, int startY) {
        this.map = map;
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision");
        this.tileX = startX;
        this.tileY = startY;

        this.x = tileX * collisionLayer.getTileWidth();
        this.y = tileY * collisionLayer.getTileHeight();

        this.texture = GameTextures.getTexture(this);
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(float deltaTime) {

    };
}
