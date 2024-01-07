package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

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
    private Player player;
    private ArrayList<Entity> entities;
    private BitmapFont font;

    public Map(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityFactory.createEntities(tiledMap);
        this.camera = camera;
        this.cameraController = new CameraController(camera, viewport);
        this.player = getPlayer();
        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, float viewportWidth, float viewportHeight) {
        // Set the view of the mapRenderer to the camera
        mapRenderer.setView(camera);

        // Render the Tiled map
        mapRenderer.render();

        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity != null) {
                entity.update(Gdx.graphics.getDeltaTime(), entities);
                entity.render(batch);
            }
            if (entity.isRemove()) {
                iterator.remove();
            }
        }

        // coin counter here
        float coinX = camera.position.x - viewportWidth / 2 + 10;
        float coinY = camera.position.y + viewportHeight / 2 - 30;
        font.draw(batch, "Coins: " + player.getCoins(), coinX, coinY);

        float fpsX = camera.position.x - viewportWidth / 2 + 10;
        float fpsY = camera.position.y + viewportHeight / 2 - 10;

        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), fpsX, fpsY);
        // Update and render the player

        player.update(Gdx.graphics.getDeltaTime(), entities);
        player.render(batch);

        cameraController.update(player);
    }

    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {

                // entities.remove(player);
                return (Player) entity;
            }
        }
        return null;
    }

    public void dispose() {
        mapRenderer.dispose();
    }
}
