package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entities.*;
import com.badlogic.gdx.maps.MapLayer;


public class EntityParser {

    public static Entity[][] getEntities(TiledMap tiledMap) {
        TiledMapTileLayer layer = (TiledMapTileLayer)tiledMap.getLayers().get(0);

        MapLayer entitiesLayer = tiledMap.getLayers().get("entities");

        Entity[][] entities = new Entity[layer.getWidth()][layer.getHeight()];

        MapObjects objects = entitiesLayer.getObjects();

        for(int i = 0; i < objects.getCount(); i++) {
            MapObject object = objects.get(i);

            float xFloat = (Float) object.getProperties().get("x");
            float yFloat = (Float) object.getProperties().get("y");

            int x = (int) (xFloat / 32);
            int y = (int) (yFloat / 32);

            int gid = (Integer) object.getProperties().get("gid");

            switch (gid) {
                case 1:
                    Player player = new Player(tiledMap, x, y);
                    entities[x][y] = player;
                    break;
                case 72:
                    Dirt dirt = new Dirt(tiledMap, x, y);
                    entities[x][y] = dirt;
                    break;   
                case 101:
                    Coin coin = new Coin(tiledMap, x, y);
                    entities[x][y] = coin;
                    break;
                default:
                    Entity entity = new Entity(tiledMap, x, y);
                    break;
            }
        }

        return entities;
    }
}
