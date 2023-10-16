package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.PlayerInputProcessor;

public class Player extends Entity{
    PlayerInputProcessor playerInputProcessor;
    
    private int moveSpeed = 3;        

    private int cooldown = 0;

    public Player(TiledMap map, int startX, int startY) {
        super(map, startX, startY);
        //this.texture = new Texture("textures/player/player_front.png");
        this.sprite = new Sprite(texture);
        this.playerInputProcessor = new PlayerInputProcessor();
        
        Gdx.input.setInputProcessor(playerInputProcessor);
    }

    public void update(float deltaTime) {
        handleInput();
    }

    public void move(int newTileX, int newTileY) {
        Boolean outOfBouds = newTileX >= 0 && newTileX < collisionLayer.getWidth() && newTileY >= 0 && newTileY < collisionLayer.getHeight();
        
        Boolean collision = collisionLayer.getCell(newTileX, newTileY) == null;
        if (outOfBouds && collision) {
            this.tileX = newTileX;
            this.tileY = newTileY;
        
            this.x = tileX * collisionLayer.getTileWidth();
            this.y = tileY * collisionLayer.getTileHeight();
        } 
    }
            
    public void handleInput() {
        if(cooldown > 0) {
            cooldown--;
            return;
        }
            if(playerInputProcessor.isLeft()) {
                move(tileX - 1, tileY);
            }
            else if(playerInputProcessor.isRight()) {
                move(tileX + 1, tileY);
            }
            else if(playerInputProcessor.isUp()) {
                move(tileX, tileY + 1);
            }
            else if(playerInputProcessor.isDown()) {
                move(tileX, tileY - 1);
            }
            if(playerInputProcessor.isLeft() || playerInputProcessor.isRight() || playerInputProcessor.isUp() || playerInputProcessor.isDown()) {
                cooldown = moveSpeed;
            } 
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    @Override
    public String toString() {
        return "Player at (" + tileX + ", " + tileY + ")";
    }
}
