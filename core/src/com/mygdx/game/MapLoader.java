package com.mygdx.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class MapLoader {
    private TiledMap tiledMap;
    private TiledMapTileLayer background, collisions, entities;
    private final int width, height, tileWidth, tileHeight;
    /** 
	 * @param fileName the filename
	  */
    public MapLoader(String file) {
        tiledMap = new TmxMapLoader().load(file);

        background = (TiledMapTileLayer)tiledMap.getLayers().get("background");
        collisions = (TiledMapTileLayer)tiledMap.getLayers().get("collisions");
        entities = (TiledMapTileLayer)tiledMap.getLayers().get("entities");

        this.width = background.getWidth();
        this.height = background.getHeight();
        this.tileWidth = background.getTileWidth();
        this.tileHeight = background.getTileHeight();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }
}
