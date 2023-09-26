package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;

public class MyGdxGame extends ApplicationAdapter {
    OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private SpriteBatch batch;
    Player player;

    @Override
    public void create() {
        float viewportWidth = 800; // Change to your desired viewport width
        float viewportHeight = 600; // Change to your desired viewport height

        batch = new SpriteBatch();
        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        viewport.apply(true);

        // Load your Tiled map
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        // Set the camera position to center it on the map
        camera.position.set(viewportWidth / 2, viewportHeight / 2, 0);

        player = new Player(tiledMap, 5, 5);

        EntityParser x = new EntityParser();
        x.getEntities(tiledMap);
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Update the camera and mapRenderer
        camera.update();
        mapRenderer.setView(camera);

        // Render the Tiled map
        mapRenderer.render();

        // Your game rendering logic can go here
        // For example, you can draw other game objects on top of the Tiled map
        player.update(Gdx.graphics.getDeltaTime());
        player.render(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        // Dispose of resources when the game is closed
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}
