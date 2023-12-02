package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private BitmapFont font;

    int viewportWidth = 640; // Ändere die gewünschte Viewport-Breite
    int viewportHeight = 360;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Initialisiere Kamera und Viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(viewportWidth, viewportHeight, camera);

        viewport.apply(true);

        // Lade die Tiled-Map
        tiledMap = new TmxMapLoader().load("maps/map2.tmx");

        map = new Map(tiledMap, camera, viewport);

        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
    }

    @Override
    public void render() {
        // Lösche den Bildschirm
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Setze die Projektionsmatrix auf den Batch
        batch.setProjectionMatrix(camera.combined);

        map.render(batch, camera, viewportWidth, viewportHeight);
        // Berechne die Position für den FPS-Zähler basierend auf der Kameraposition
        float fpsX = camera.position.x - viewportWidth / 2 + 10;
        float fpsY = camera.position.y + viewportHeight / 2 - 10;

        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), fpsX, fpsY);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        // Gib Ressourcen frei, wenn das Spiel beendet wird
        tiledMap.dispose();
        batch.dispose();
        font.dispose();
    }
}