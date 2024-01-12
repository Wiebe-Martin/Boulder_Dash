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

public class BoulderDash {

    public enum GameState {
        GAMING,
        GAME_OVER
    }

    private GameState state = GameState.GAMING;
    private OrthogonalTiledMapRenderer mapRenderer;
    private CameraController cameraController;
    private Player player;
    private ArrayList<Entity> entities;
    private BitmapFont font;

    public BoulderDash(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityFactory.createEntities(tiledMap);
        this.cameraController = new CameraController(camera, viewport);
        this.player = getPlayer();
        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
    }

    public Player getPlayer() {
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                return (Player) entity;
            }
        }
        return null;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, float viewportWidth, float viewportHeight) {
        switch (state) {
            case GAMING:
                renderGaming(batch, camera, viewportWidth, viewportHeight);
                break;
            case GAME_OVER:
                // renderDeath();
                renderGaming(batch, camera, viewportWidth, viewportHeight);
                return;

        }
    }

    private void renderGaming(SpriteBatch batch, OrthographicCamera camera, float viewportWidth, float viewportHeight) {
        if (player.dead) {
            state = GameState.GAME_OVER;

        }

        renderMap(batch, camera);
        renderEntities(batch);
        renderCoinCounter(batch, camera, viewportWidth, viewportHeight);
        renderFPSCounter(batch, camera, viewportWidth, viewportHeight);
        updatePlayer();
        updateCamera();
    }

    private void renderMap(SpriteBatch batch, OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    private void renderEntities(SpriteBatch batch) {
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
    }

    private void renderCoinCounter(SpriteBatch batch, OrthographicCamera camera, float viewportWidth,
            float viewportHeight) {
        float coinX = camera.position.x - viewportWidth / 2 + 10;
        float coinY = camera.position.y + viewportHeight / 2 - 30;
        font.draw(batch, "Coins: " + player.getCoins(), coinX, coinY);
    }

    private void renderFPSCounter(SpriteBatch batch, OrthographicCamera camera, float viewportWidth,
            float viewportHeight) {
        float fpsX = camera.position.x - viewportWidth / 2 + 10;
        float fpsY = camera.position.y + viewportHeight / 2 - 10;
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), fpsX, fpsY);
    }

    private void updatePlayer() {
        player.update(Gdx.graphics.getDeltaTime(), entities);
    }

    private void updateCamera() {
        cameraController.update(player);
    }

    public void dispose() {
        mapRenderer.dispose();
    }
}
