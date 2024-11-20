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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGameTest;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

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
        titleParameter.size = 82; // Set the size you want
        titleFont = titleGenerator.generateFont(titleParameter);
        titleGenerator.dispose();

        FreeTypeFontGenerator messageGenerator = new FreeTypeFontGenerator(
                Gdx.files.internal("fonts/CutiveMono-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter messageParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        messageParameter.size = (int) (titleParameter.size * 0.618); // Set the size you want
        messageFont = messageGenerator.generateFont(messageParameter);
        messageGenerator.dispose();
    }

    private float opacity = 1f;
    private float opacitySpeed = 2f;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        float titleX = camera.viewportWidth / 2 - new GlyphLayout(titleFont, "Boulder Dash").width / 2;
        float titleY = (float) (camera.viewportHeight * 0.55 + titleFont.getLineHeight() / 2);
        titleFont.setColor(1, 1, 1, 1); // Set the opacity
        titleFont.draw(batch, "Boulder Dash", titleX, titleY);

        float messageX = camera.viewportWidth / 2 - new GlyphLayout(messageFont, "Press SPACE to play").width / 2;
        float messageY = (float) (camera.viewportHeight * 0.45 - messageFont.getLineHeight() / 2);
        messageFont.setColor(1, 1, 1, opacity); // Set the opacity
        messageFont.draw(batch, "SPACE zum starten ...", messageX, messageY);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game)); // Replace PlayScreen with your actual gameplay screen
        }

        // Update the opacity
        opacity += opacitySpeed * delta;
        if (opacity > 1f || opacity < 0f) {
            opacitySpeed *= -1; // Reverse the opacity speed when reaching the limits
        }
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        camera.viewportWidth = aspectRatio * camera.viewportHeight;
        camera.update();
        stage.getViewport().update(width, height, true);

        titleFont.getData().setScale(width / 800f);
        messageFont.getData().setScale(width / 1200f);
        opacity += opacitySpeed * Gdx.graphics.getDeltaTime();
        if (opacity > 1f || opacity < 0f) {
            opacitySpeed *= -1; // Reverse the opacity speed when reaching the limits
        }

    }

    // Other methods remain unchanged

    @Override
    public void dispose() {
        stage.dispose();
        titleFont.dispose();
        messageFont.dispose();
        batch.dispose();
    }

    // Other methods remain unchanged

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
}
