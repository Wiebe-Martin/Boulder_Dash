package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Explosion;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Stone;
import com.mygdx.game.rendering.CameraController;
import com.mygdx.game.rendering.EntityFactory;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class BoulderDash {

    public enum GameState {
        GAMING,
        GAME_OVER,
        GAME_WIN
    }

    private GameState state = GameState.GAMING;
    private OrthogonalTiledMapRenderer mapRenderer;
    private CameraController cameraController;
    private Player player;
    private ArrayList<Entity> entities;
    private BitmapFont font;
    private TiledMap map;
    private TiledMapTileLayer collisionLayer;
    private TiledMapTileLayer dirtLayer;

    private int maxCoins = 0;
    private int countdown = 120;

    public BoulderDash(TiledMap tiledMap, OrthographicCamera camera, Viewport viewport) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.entities = EntityFactory.createEntities(tiledMap);
        this.cameraController = new CameraController(camera, viewport);
        this.player = getPlayer();
        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
        this.map = tiledMap;
        this.collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision");
        this.dirtLayer = (TiledMapTileLayer) map.getLayers().get("dirt");

        // get how manny coins are in the entities list

        for (Entity entity : entities) {
            if (entity instanceof Coin) {
                maxCoins++;
            }
        }
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
                renderGameOver(batch, camera, viewportWidth, viewportHeight);
                return;

            case GAME_WIN:
                // System.out.println("You win!");
                // nextLevel();
                return;

        }
    }

    private void prepareGameOver(Player player) {
        int tileX = player.getTileX();
        int tileY = player.getTileY();

        ArrayList<int[]> explodeLocations = new ArrayList<int[]>();
        explodeLocations.add(new int[] { tileX - 1, tileY - 1 });
        explodeLocations.add(new int[] { tileX, tileY - 1 });
        explodeLocations.add(new int[] { tileX + 1, tileY - 1 });
        explodeLocations.add(new int[] { tileX - 1, tileY });
        explodeLocations.add(new int[] { tileX + 1, tileY });
        explodeLocations.add(new int[] { tileX - 1, tileY + 1 });
        explodeLocations.add(new int[] { tileX, tileY + 1 });
        explodeLocations.add(new int[] { tileX + 1, tileY + 1 });

        Iterator<Entity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            int entityX = entity.getTileX();
            int entityY = entity.getTileY();

            Iterator<int[]> explodeIterator = explodeLocations.iterator();
            while (explodeIterator.hasNext()) {
                int[] explodeLocation = explodeIterator.next();
                if (entityX == explodeLocation[0] && entityY == explodeLocation[1]) {
                    entity.explode();
                    explodeIterator.remove();
                }
            }
        }

        Iterator<int[]> explodeIterator = explodeLocations.iterator();
        while (explodeIterator.hasNext()) {

            int[] explosionTile = explodeIterator.next();

            if (collisionLayer.getCell(explosionTile[0], explosionTile[1]) != null) {
                collisionLayer.setCell(explosionTile[0], explosionTile[1], null);
            }
            if (dirtLayer.getCell(explosionTile[0], explosionTile[1]) != null) {
                dirtLayer.setCell(explosionTile[0], explosionTile[1], null);
            }

            entities.add(new Explosion(map, explosionTile[0], explosionTile[1]));

        }

    }

    private void renderGameOver(SpriteBatch batch, OrthographicCamera camera, float viewportWidth,
            float viewportHeight) {

        renderMap(batch, camera);
        renderEntities(batch);
        renderCoinCounter(batch, camera, viewportWidth, viewportHeight);
        renderFPSCounter(batch, camera, viewportWidth, viewportHeight);
        updatePlayer();
        updateCamera();

    }

    private float countdownTimer = 1.0f; // Countdown timer in seconds
    private float elapsedTime = 0.0f; // Elapsed time since last countdown update

    private void renderGaming(SpriteBatch batch, OrthographicCamera camera, float viewportWidth, float viewportHeight) {
        if (player.dead) {
            state = GameState.GAME_OVER;
            prepareGameOver(player);
        }

        if (player.getCoins() == maxCoins && countdown > 0 && !player.dead && player.isBase()) {
            state = GameState.GAME_WIN;
        }

        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime >= countdownTimer) {
            elapsedTime -= countdownTimer;
            if (countdown > 0) {
                countdown--;
            } else {
                player.explode();
                state = GameState.GAME_OVER;
            }
        }

        renderMap(batch, camera);
        renderEntities(batch);
        renderCoinCounter(batch, camera, viewportWidth, viewportHeight);
        renderFPSCounter(batch, camera, viewportWidth, viewportHeight);
        renderCountdown(batch, camera, viewportWidth, viewportHeight);
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

        font.draw(batch, ("Coins: " + player.getCoins() + " / " + maxCoins), coinX, coinY);
    }

    private void renderCountdown(SpriteBatch batch, OrthographicCamera camera, float viewportWidth,
            float viewportHeight) {
        float coinX = camera.position.x - viewportWidth / 2 + 10;
        float coinY = camera.position.y + viewportHeight / 2 - 50;
        font.draw(batch, ("Zeit verbleibend:" + countdown), coinX, coinY);
    }

    private void renderFPSCounter(SpriteBatch batch, OrthographicCamera camera, float viewportWidth,
            float viewportHeight) {
        float fpsX = camera.position.x - viewportWidth / 2 + 10;
        float fpsY = camera.position.y + viewportHeight / 2 - 10;
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), fpsX, fpsY);
    }

    public GameState getState() {
        return state;
    }

    private void updatePlayer() {
        player.update(Gdx.graphics.getDeltaTime(), entities);
    }

    private void updateCamera() {
        cameraController.update(player);
    }

    public void dispose() {
        mapRenderer.dispose();
        font.dispose();
        map.dispose();
        collisionLayer.dispose();
        dirtLayer.dispose();
    }
}
