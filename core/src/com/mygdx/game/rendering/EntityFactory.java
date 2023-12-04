package com.mygdx.game.rendering;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityFactory {
    private static HashMap<Integer, EntityFactoryFunction> entityFactoryMap = new HashMap<>();

    static {
        // hier werden die gid's den jeweiligen Klassen zugeordnet
        entityFactoryMap.put(1, Player::new);
        entityFactoryMap.put(71, Stone::new);
        entityFactoryMap.put(101, Coin::new);
    }

    public static ArrayList<Entity> createEntities(TiledMap tiledMap) {
        MapObjects objects = tiledMap.getLayers().get("entities").getObjects();
        ArrayList<Entity> entitiesList = new ArrayList<>();

        for (int i = 0; i < objects.getCount(); i++) {
            MapObject object = objects.get(i);

            float xFloat = (Float) object.getProperties().get("x");
            float yFloat = (Float) object.getProperties().get("y");

            int x = (int) (xFloat / 32);
            int y = (int) (yFloat / 32);

            int gid = (Integer) object.getProperties().get("gid");

            // Create an instance of the entity using the factory function
            Entity entity = createEntity(gid, tiledMap, x, y);
            entitiesList.add(entity);
        }

        return entitiesList;
    }

    private static Entity createEntity(int gid, TiledMap tiledMap, int x, int y) {
        EntityFactoryFunction factoryFunction = entityFactoryMap.get(gid);
        return factoryFunction.create(tiledMap, x, y);
    }

    private interface EntityFactoryFunction {
        Entity create(TiledMap tiledMap, int x, int y);
    }

    // private static class DefaultEntity implements Entity {
    //     // Default implementation or fallback if no specific entity is found
    //     // You might want to customize this based on your needs
    //     public DefaultEntity(TiledMap tiledMap, int x, int y) {
    //         // Your default entity constructor logic
    //     }
    // }
}

