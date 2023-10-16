package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.entities.Entity;

public class GameTextures {
    /* 
    public static Texture getTexture(Entity entity) {
        Texture tiles = new Texture(Gdx.files.internal("maps/sprites/sprites.png"));
	    TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);

        int gid = EntityFactory.getGidForEntity(entity.getClass()) - 1;

        int tx = gid % 10;
        int ty = gid / 10;

        TextureRegion region = splitTiles[tx][ty];

        Texture texture = region.getTexture().getTextureData();

        System.out.println(tx + "/" + ty);

        return texture;
    }
    */
    
    public static Texture getTexture(Entity entity) {
        Texture tiles = new Texture(Gdx.files.internal("maps/sprites/sprites.png"));
        TextureRegion[][] splitTiles = TextureRegion.split(tiles, 32, 32);
    
        int gid = EntityFactory.getGidForEntity(entity.getClass()) - 1;
    
        int tx = gid % 10;
        int ty = gid / 10;
    
        TextureRegion region = splitTiles[tx][ty]; // Get the TextureRegion

        // Get the width and height of the TextureRegion
        int regionWidth = region.getRegionWidth();
        int regionHeight = region.getRegionHeight();

        Pixmap pixmap = new Pixmap(regionWidth, regionHeight, Pixmap.Format.RGBA8888);
        pixmap.draw(region, 0, 0);

        // Create a new Texture using the TextureRegion
        Texture texture = new Texture(region.getTexture().getTextureData());

        System.out.println(tx + "/" + ty);

        return texture;
    }
    
}
