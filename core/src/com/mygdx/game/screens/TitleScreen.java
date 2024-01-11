package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGameTest;

public class TitleScreen implements Screen {

    private MyGdxGameTest game;
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private BitmapFont titleFont;
    private BitmapFont messageFont;

    public TitleScreen(MyGdxGameTest game) {
        this.game = game;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Load your custom fonts here
        FreeTypeFontGenerator titleGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/RubikGlitch-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 128; // Set the size you want
        titleFont = titleGenerator.generateFont(titleParameter);
        titleGenerator.dispose();

        FreeTypeFontGenerator messageGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/CutiveMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter messageParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        messageParameter.size = 46; // Set the size you want
        messageFont = messageGenerator.generateFont(messageParameter);
        messageGenerator.dispose();
    }

    // Other methods remain unchanged

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        titleFont.draw(batch, "Boulder Dash", camera.viewportWidth / 2 - 100, camera.viewportHeight / 2 + 50);
        messageFont.draw(batch, "Press SPACE to play", camera.viewportWidth / 2 - 130, camera.viewportHeight / 2 - 50);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game)); // Replace PlayScreen with your actual gameplay screen
        }
    }

    // Other methods remain unchanged

    @Override
    public void dispose() {
        stage.dispose();
        titleFont.dispose();
        messageFont.dispose();
    }

    @Override
    public void pause() {
        // Implement pause logic here
    }

    @Override
    public void show() {
        // Implement show logic here
    }

    @Override
    public void hide() {
        // Implement hide logic here
    }

    @Override
    public void resume() {
        // Implement resume logic here
    }

    @Override
    public void resize(int width, int height) {
        // Implement resize logic here
    }
}
