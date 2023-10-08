package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;


public class CameraController {
    Camera camera;
    Player player;
    Viewport viewport;
    final int SAFEBUFFER = 200;

    int animationTime = 0;
    int animationTimeMax = 2;
    boolean animating = false;
    float animationX = 0;
    float animationY = 0;
    

    public CameraController(Camera camera, Player player, Viewport viewport) {
        this.camera = camera;
        this.player = player;
        this.viewport = viewport;
    }

    public void update() {
        if((player.getX() > camera.position.x + SAFEBUFFER) || (player.getX() < camera.position.x - SAFEBUFFER) || (player.getY() > camera.position.y + SAFEBUFFER) || (player.getY() < camera.position.y - SAFEBUFFER)) {
            
            animateTo(player.getX(), player.getY());
        }
        animateCamera();
        
        camera.update();
    }

    public void animateTo(float x, float y) {
        animating = true;
        animationX = x;
        animationY = y;
    }

    public void animateCamera() {
        if(animating) {
            if(animationTime > animationTimeMax) {
                animating = false;
                animationTime = 0;
            }
            animationTime ++;
            camera.position.set(camera.position.x + (animationX - camera.position.x) / animationTimeMax, camera.position.y + (animationY - camera.position.y) / animationTimeMax, 0);
        }
    }

}
