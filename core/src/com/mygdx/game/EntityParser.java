package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

public class EntityParser {
    private ArrayList<Entity> entities;
    
    public void getEntities(TiledMap tiledMap) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("spawnpoints");
        int width = layer.getWidth();
        int height = layer.getHeight();

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Cell cell = layer.getCell(i,j);

                if (cell != null) {
                    TiledMapTile tile = cell.getTile();
                    // Access custom property
                    String entityType = (String) tile.getProperties().get("type");
                    System.out.println(entityType);
                    // Depending on the entityType, create the corresponding entity
                    if ("Player".equals(entityType)) {
                        // Create a player entity
                        System.out.println(i + "," + j);
                    }
                }
            }
        }
        System.out.println("End");
    }
}
