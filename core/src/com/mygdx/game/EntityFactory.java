package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.mygdx.game.entities.*;

import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;


public class EntityFactory {
    private static HashMap<Integer, Class<? extends Entity>> entityMap = new HashMap<>();
    
    static {
        // Define entity mappings here
        entityMap.put(1, Player.class);
        entityMap.put(72, Dirt.class);
        entityMap.put(101, Coin.class);
    }


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

            // Determine the entity class based on gid
            Class<? extends Entity> entityClass = getEntityClass(gid);

            try {
                // Create an instance of the entity and store it in the entities array
                Entity entity = entityClass.getConstructor(TiledMap.class, int.class, int.class).newInstance(tiledMap, x, y);
                entities[x][y] = entity;
            } catch (Exception e) {
                // Handle any exceptions (e.g., if the class or constructor doesn't exist)
                e.printStackTrace();
            }
        }

        return entities;
    }

    public static Class<? extends Entity> getEntityClass(int gid) {
        return entityMap.getOrDefault(gid, Entity.class);
    }

    public static Integer getGidForEntity(Class<? extends Entity> entityClass) {
        for (HashMap.Entry<Integer, Class<? extends Entity>> entry : entityMap.entrySet()) {
            if (entry.getValue().equals(entityClass)) {
                return entry.getKey();
            }
        }

        return null; // Entity class not found in the map
    }
}
