package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class PlayerInputProcessor extends InputAdapter {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                left = true;
                break;
            case Keys.RIGHT:
                right = true;
                break;
            case Keys.UP:
                up = true;
                break;
            case Keys.DOWN:
                down = true;
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
                left = false;
                break;
            case Keys.RIGHT:
                right = false;
                break;
            case Keys.UP:
                up = false;
                break;
            case Keys.DOWN:
                down = false;
                break;
        }
        return true;
    }
}