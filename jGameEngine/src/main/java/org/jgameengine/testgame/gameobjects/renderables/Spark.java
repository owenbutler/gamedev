package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

/**
 * User: Owen Butler
 * Date: 11/04/2005
 * Time: 17:12:46
 */
public class Spark
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     */
    public Spark(float x, float y) {
        super("spark/spark_01.png", x, y, 1, 1);
        setScreenClipRemove(true);

        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(1, 1);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}
