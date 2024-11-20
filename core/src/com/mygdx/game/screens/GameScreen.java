package com.mygdx.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BoulderDash;
import com.mygdx.game.MyGdxGameTest;

public class GameScreen extends ScreenAdapter {
    MyGdxGameTest game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;

    private BoulderDash boulderDash;

    private int viewportWidth = 800; // Ändere die gewünschte Viewport-Breite
    private int viewportHeight = 600;

    private ArrayList<String> levelNames = new ArrayList<>();
    private int currentLevel = 1;
    private int gameOverCooldown = 40;

    private int gameOverCooldownCounter = gameOverCooldown;

    public GameScreen(MyGdxGameTest game) {
        this.game = game;
        levelNames.add("level0");
        levelNames.add("level1");
        levelNames.add("level2");
        // Initialisiere Kamera und Viewport
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(viewportWidth, viewportHeight, camera);

        resetGame(levelNames.get(currentLevel));
    }

    @Override
    public void render(float delta) {
        // Lösche den Bildschirm
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        update();

        // Setze die Projektionsmatrix auf den Batch
        game.batch.setProjectionMatrix(camera.combined);

        boulderDash.render(game.batch, camera, viewportWidth, viewportHeight);

        game.batch.end();
    }

    public void update() {
        if (boulderDash.getState() == BoulderDash.GameState.GAME_WIN) {
            System.out.println("Loading new level!");
            currentLevel++;
            if (currentLevel >= levelNames.size()) {
                currentLevel = 1;
            }
            loadLevel(levelNames.get(currentLevel));
        }

        if (boulderDash.getState() == BoulderDash.GameState.GAME_OVER) {

            if (gameOverCooldownCounter > 0) {
                gameOverCooldownCounter--;
                return;
            }
            gameOverCooldownCounter = gameOverCooldown;
            loadLevel(levelNames.get(currentLevel));
        }
    }

    @Override
    public void show() {

    }

    public void resetGame(String levelName) {
        viewport.apply(true);

        // Lade die Tiled-Map
        tiledMap = new TmxMapLoader().load("maps/" + levelName + ".tmx");

        boulderDash = new BoulderDash(tiledMap, camera, viewport);
    }

    public void loadLevel(String levelName) {
        resetGame(levelName);
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        camera.viewportWidth = aspectRatio * camera.viewportHeight;
        camera.update();
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        boulderDash.dispose();
    }
}
