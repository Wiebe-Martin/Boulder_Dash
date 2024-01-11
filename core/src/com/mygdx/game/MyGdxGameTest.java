package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.TitleScreen;

public class MyGdxGameTest extends Game {
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(255, 255, 255, 255);
        setScreen(new TitleScreen(this));
    }

    @Override
    public void dispose() {
        // Gib Ressourcen frei, wenn das Spiel beendet wird
        batch.dispose();
    }

}