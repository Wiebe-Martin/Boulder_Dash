package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;


public class CameraController {
    Camera camera;
    Viewport viewport;
    
    int animationTime = 0;
    int animationTimeMax = 2;
    boolean animating = false;
    float animationX = 0;
    float animationY = 0;
    

    public CameraController(Camera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    public void update(Player player) {
        if((player.getX() > camera.position.x + (viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 10) || 
        (player.getX() < camera.position.x - (viewport.getWorldWidth() / 2) + (viewport.getWorldWidth() / 10)) || 
        (player.getY() > camera.position.y + (viewport.getWorldHeight() / 2) - (viewport.getWorldHeight() / 10)) || 
        (player.getY() < camera.position.y - (viewport.getWorldHeight() / 2) + (viewport.getWorldHeight() / 10)))) {
            
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
