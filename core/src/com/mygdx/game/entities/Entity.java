package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import java.util.ArrayList;


public class Entity {
    protected Sprite sprite;
    protected Texture texture;
    protected float x, y;

    protected int tileX, tileY;
    protected TiledMap map;
    protected TiledMapTileLayer collisionLayer;
    protected TiledMapTileLayer dirtLayer;
    protected float moveSpeed;
    protected ArrayList<Entity> entities = new ArrayList<Entity>();

    public Entity(TiledMap map, int startX, int startY) {
        this.map = map;
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision");
        this.dirtLayer = (TiledMapTileLayer) map.getLayers().get("dirt");
        this.tileX = startX;
        this.tileY = startY;


        this.x = tileX * collisionLayer.getTileWidth();
        this.y = tileY * collisionLayer.getTileHeight();
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

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void update(float deltaTime, ArrayList<Entity> entities) {
        this.entities = entities;
    }

    
    protected boolean isAir(int x, int y) {
        return collisionLayer.getCell(x, y) == null && !isDirt(x, y) && !isStone(x, y);
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

    public void handleCollison() {
        System.out.println(getTileX() + "/" + getTileY());
    };
}
