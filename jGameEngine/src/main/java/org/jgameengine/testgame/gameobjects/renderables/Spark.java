package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class Spark
        extends BaseDrawableGameObject {

    public Spark(float x, float y) {
        super("spark/spark_01.png", x, y, 1, 1);
        setScreenClipRemove(true);

        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(1, 1);
    }


    public void think() {
        baseDrawableThink();
    }
}
