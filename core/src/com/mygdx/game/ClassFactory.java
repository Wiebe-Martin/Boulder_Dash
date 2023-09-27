package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

import com.mygdx.game.entities.Coin;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

public class ClassFactory {
    private Map<String, Class<?>> classMap;

    public ClassFactory() {
        classMap = new HashMap<>();
    }

    public Entity getEntity(String className) {
        switch (className) {
            case "Coin":
                Coin coin = new Coin(null, 0, 0);
                break;
        }
    }
}


