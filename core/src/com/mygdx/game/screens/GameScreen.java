package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BoulderDash;
import com.mygdx.game.MyGdxGameTest;

public class GameScreen extends ScreenAdapter {
    MyGdxGameTest game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;

    private BoulderDash boulderDash;

    private int viewportWidth = 640; // Ändere die gewünschte Viewport-Breite
    private int viewportHeight = 360;

    public GameScreen(MyGdxGameTest game) {
        this.game = game;

        // Initialisiere Kamera und Viewport
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(viewportWidth, viewportHeight, camera);

        viewport.apply(true);

        // Lade die Tiled-Map
        tiledMap = new TmxMapLoader().load("maps/map2.tmx");

        boulderDash = new BoulderDash(tiledMap, camera, viewport);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Lösche den Bildschirm
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Setze die Projektionsmatrix auf den Batch
        game.batch.setProjectionMatrix(camera.combined);

        boulderDash.render(game.batch, camera, viewportWidth, viewportHeight);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewportHeight = height;
        viewportWidth = width;
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
