package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.mygdx.game.entities.Entity;

import com.badlogic.gdx.maps.MapLayer;


public class EntityParser {
    private ArrayList<Entity> entities;



    enum entityType{
        STONE,
        DIRT,
        COIN
    }
    // Player 1; Dirt 72; Coin 101
    public static void getEntities(TiledMap tiledMap) {
        MapLayer layer = tiledMap.getLayers().get("entities");
        MapObjects objects = layer.getObjects();

        for(int i = 0; i < objects.getCount(); i++){
            MapObject object = objects.get(i);

/*  
            switch(objects.get(i).getProperties().get("GID")){
                case 72:
                    //dirt erstellen
                break;
                case 
            }
*/
        }
    }
}
