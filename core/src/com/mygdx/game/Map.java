package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;
import com.mygdx.game.rendering.CameraController;
import com.mygdx.game.rendering.EntityFactory;

public class Map {
    private OrthogonalTiledMapRenderer mapRenderer;
    private CameraController cameraController;
    private OrthographicCamera camera;

    private ArrayList<Entity> entities;
    private Player player;

    

    public Map(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityFactory.getEntities(tiledMap);
        this.camera = camera;
        this.cameraController = new CameraController(camera, viewport);
        this.getPlayer();
    }
    
    public void render(SpriteBatch batch) {
        // Set the view of the mapRenderer to the camera
        mapRenderer.setView(camera);

        // Render the Tiled map
        mapRenderer.render();

        // Render all entities
        for (Entity entity : entities) {
            if (entity != null) {
                entity.update(Gdx.graphics.getDeltaTime());
                entity.render(batch);

                if(entity.getTileX() == player.getTileX() && entity.getTileY() == player.getTileY()) {
                    entity.handleCollison();
                }
            }
        }

        player.update(Gdx.graphics.getDeltaTime());
        player.render(batch);

        cameraController.update(player);
    }

    public void getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                player = (Player) entity;
                entities.remove(player);
                return;
            }
        }
    }
    
    
    public void dispose() {
        mapRenderer.dispose();
    }
}
