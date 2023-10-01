package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;

public class CameraController {
    Camera camera;
    Player player;
    Viewport viewport;
    public CameraController(Camera camera, Player player,   Viewport viewport) {
        this.camera = camera;
        this.player = player;
        this.viewport = viewport;
    }

    public void update() {
        if(player.getX() > viewport.getWorldWidth() / 2 && player.getX() < 1000 - viewport.getWorldWidth() / 2) {
            camera.position.set(player.getX(), player.getY(), 0);

        }

        camera.update();
    }

}
