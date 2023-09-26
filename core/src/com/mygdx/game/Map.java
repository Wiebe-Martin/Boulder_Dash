package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entities.Entity;

public class Map {
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ArrayList<Entity> entities;

    public Map(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = new ArrayList<Entity>();
    }
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void update(float deltaTime) {
        // Update all entities
        for (Entity entity : entities) {
            entity.update(deltaTime);
        }
    }

    public void render(SpriteBatch spriteBatch) {
        mapRenderer.render();

        // Render all entities
        spriteBatch.begin();
        for (Entity entity : entities) {
            entity.render(spriteBatch);
        }
        spriteBatch.end();
    }
}
