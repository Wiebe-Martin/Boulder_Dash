package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Player;

public class CameraController {
    // Deklariere Variablen
    Camera camera;
    Viewport viewport;
    int animationTime = 0;
    int animationTimeMax = 2;
    boolean animating = false;
    float animationX = 0;
    float animationY = 0;

    // Konstruktor
    public CameraController(Camera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    // Update-Methode
    public void update(Player player) {
        // Überprüfe, ob sich der Spieler außerhalb des Kamerabereichs befindet
        if((player.getX() > camera.position.x + (viewport.getWorldWidth() / 2) - (viewport.getWorldWidth() / 10) || 
        (player.getX() < camera.position.x - (viewport.getWorldWidth() / 2) + (viewport.getWorldWidth() / 10)) || 
        (player.getY() > camera.position.y + (viewport.getWorldHeight() / 2) - (viewport.getWorldHeight() / 10)) || 
        (player.getY() < camera.position.y - (viewport.getWorldHeight() / 2) + (viewport.getWorldHeight() / 10)))) {
            
            // Animiere Kamera zur Position des Spielers
            animateTo(player.getX(), player.getY());
        }
        // Animiere Kamera
        animateCamera();
        
        // Aktualisiere Kamera
        camera.update();
    }

    // Animiere Kamera zur angegebenen Position
    public void animateTo(float x, float y) {
        animating = true;
        animationX = x;
        animationY = y;
    }

    // Animiere Kamera
    public void animateCamera() {
        if(animating) {
            if(animationTime > animationTimeMax) {
                animating = false;
                animationTime = 0;
            }
            animationTime ++;
            // Bewege Kamera zur Zielposition
            camera.position.set(camera.position.x + (animationX - camera.position.x) / animationTimeMax, camera.position.y + (animationY - camera.position.y) / animationTimeMax, 0);
        }
    }
}