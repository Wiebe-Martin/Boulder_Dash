package com.mygdx.game.rendering;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator implements ApplicationListener {

	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 10, FRAME_ROWS = 30;

	// Objects used
	static Texture walkSheet = new Texture(Gdx.files.internal("maps/sprites/sprites.png"));
	SpriteBatch spriteBatch;
	static TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				32,
				32);

	// A variable for tracking elapsed time for the animation
	float stateTime;

	public void create() {

	}

	public static Animation<TextureRegion> getAnimation(int[] index) {
		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] frames = new TextureRegion[index.length];

		for (int i = 0; i < index.length; i++) {
			frames[i] = tmp[index[i] / 10]
						   [index[i] % 10];
		}

		// Initialize the Animation with the frame interval and array of frames
		return new Animation<TextureRegion>(0.0333f, frames);
	}

	@Override
	public void render() {
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		//stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		//TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		//spriteBatch.begin();
		//spriteBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
		//spriteBatch.end();
	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		spriteBatch.dispose();
		walkSheet.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'resize'");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'pause'");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'resume'");
	}
}

