package com.mygdx.game.utils;

import java.security.Key;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class PlayerInputProcessor extends InputAdapter {
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean shift;

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

    public boolean isShift() {
        return shift;
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
            case Keys.SHIFT_LEFT:
                shift = true;
                break;
            case Keys.SHIFT_RIGHT:
                shift = true;
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
            case Keys.SHIFT_LEFT:
                shift = false;
                break;
            case Keys.SHIFT_RIGHT:
                shift = false;
                break;
        }
        return true;
    }
}