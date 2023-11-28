package com.mygdx.game.rendering;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.entities.*;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.maps.MapLayer;


public class EntityFactory {
    private static HashMap<Integer, Class<? extends Entity>> entityMap = new HashMap<>();
    
    static {
        // Define entity mappings here
        entityMap.put(1, Player.class);
        entityMap.put(71, Stone.class);
        entityMap.put(101, Coin.class);
    }


    public static ArrayList<Entity> getEntities(TiledMap tiledMap) {
        MapLayer entitiesLayer = tiledMap.getLayers().get("entities");
    
        ArrayList<Entity> entitiesList = new ArrayList<>();
    
        MapObjects objects = entitiesLayer.getObjects();
    
        for (int i = 0; i < objects.getCount(); i++) {
            MapObject object = objects.get(i);
    
            float xFloat = (Float) object.getProperties().get("x");
            float yFloat = (Float) object.getProperties().get("y");
    
            int x = (int) (xFloat / 32);
            int y = (int) (yFloat / 32);
    
            int gid = (Integer) object.getProperties().get("gid");
    
            // Determine the entity class based on gid
            Class<? extends Entity> entityClass = getEntityClass(gid);
    
            try {
                // Create an instance of the entity and add it to the ArrayList
                Entity entity = entityClass.getConstructor(TiledMap.class, Integer.TYPE, Integer.TYPE).newInstance(tiledMap, x, y);
                entitiesList.add(entity);
            } catch (Exception e) {
                // Handle any exceptions (e.g., if the class or constructor doesn't exist)
                e.printStackTrace();
            }
        }
    
        return entitiesList;
    }
    
    public static Class<Entity> getEntityClass(int gid) {
    return (Class<Entity>) entityMap.getOrDefault(gid, Entity.class);
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
