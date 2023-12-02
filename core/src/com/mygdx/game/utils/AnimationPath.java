package com.mygdx.game.utils;
import java.util.ArrayList;

public class AnimationPath {

    private ArrayList<AnimationPoint> animationPoints = new ArrayList<>();

    public AnimationPath() {
    }

    public void addPoint(int x, int y) {
        animationPoints.add(new AnimationPoint(x, y));
    }

    public AnimationPoint getNext() {
        if (animationPoints.size() > 0) {
            return animationPoints.remove(0);
        } else {
            return null;
        }
    }
    public boolean hasNext() {
        return animationPoints.size() > 0;
    }

    public void clear() {
        animationPoints.clear();
    }
    
}
