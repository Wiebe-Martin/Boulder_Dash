package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;
    private SpriteBatch batch;
    private Map map;

    @Override
    public void create() {
        int viewportWidth = 640; // Change to your desired viewport width
        int viewportHeight = 360; // Change to your desired viewport height

        batch = new SpriteBatch();
        // Initialize the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);
        
        viewport.apply(true);
    
        // Load your Tiled map
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");

        map = new Map(tiledMap, camera, viewport);
    }

    @Override
    public void render() {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        map.render(batch);
       
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
    }
}
