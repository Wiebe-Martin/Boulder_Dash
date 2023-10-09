package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

public class Map {
    private OrthogonalTiledMapRenderer mapRenderer;
    private Entity entities[][];
    private Player player;
    private CameraController cameraController;
    private OrthographicCamera camera;

    public Map(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityParser.getEntities(tiledMap);
        this.camera = camera;
        this.cameraController = new CameraController(camera, viewport);
        this.getPlayer();
    }
    
    public void removeEntity(int x, int y) {
        entities[x][y] = null;
    }

    public void render(SpriteBatch batch) {
        // Set the view of the mapRenderer to the camera
        mapRenderer.setView(camera);

        // Render the Tiled map
        mapRenderer.render();

        // Render all entities
        for (int i = 0; i < entities.length; i++) {
            for (int j = 0; j < entities[i].length; j++) {
                Entity entity = entities[i][j];
                if (entity != null) {
                    entity.render(batch);
                }
            }
        }
        
        player.update(Gdx.graphics.getDeltaTime());
        player.render(batch);

        cameraController.update(player);

        handelCollison();
    }

    public void getPlayer() {
        for (int i = 0; i < entities.length; i++) {
            for (int j = 0; j < entities[i].length; j++) {
                Entity entity = entities[i][j];

                if (entity != null) {
                    if(entity instanceof Player) {
                        player = (Player) entity;
                        removeEntity(i, j);
                        return;
                    }
                }
            }
        }
    }

    public void handelCollison() {
        int playerX = player.getTileX();
        int playerY = player.getTileY();

        if(entities[playerX][playerY] != null) {
            removeEntity(playerX, playerY);
        }
    }

    public void dispose() {
        mapRenderer.dispose();
    }
}
