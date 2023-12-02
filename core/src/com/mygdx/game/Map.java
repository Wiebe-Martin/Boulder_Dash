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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Map {
    private OrthogonalTiledMapRenderer mapRenderer;
    private CameraController cameraController;
    private OrthographicCamera camera;

    private ArrayList<Entity> entities;
    private Player player;
    private BitmapFont font;
    

    public Map(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityFactory.createEntities(tiledMap);
        this.camera = camera;
        this.cameraController = new CameraController(camera, viewport);
        this.getPlayer();
        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
    }
    
    public void render(SpriteBatch batch, OrthographicCamera camera, float viewportWidth, float viewportHeight) {
        // Set the view of the mapRenderer to the camera
        mapRenderer.setView(camera);

        // Render the Tiled map
        mapRenderer.render();

        // Render all entities
        for (Entity entity : entities) {
            if (entity != null) {
                entity.update(Gdx.graphics.getDeltaTime(), entities);
                entity.render(batch);
            }
        }

        //coin counter here
        float fpsX = camera.position.x - viewportWidth / 2 + 10;
        float fpsY = camera.position.y + viewportHeight / 2 - 30;
        font.draw(batch, "Coins: " + player.getCoins(), fpsX, fpsY);

        // Update and render the player

        player.update(Gdx.graphics.getDeltaTime(), entities);
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
