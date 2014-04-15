package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class BulletSpark
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x x position
     * @param y y position
     */
    public BulletSpark(float x, float y) {
        super(AssetConstants.gfx_spark, x, y, 4, 4);
        setScreenClipRemove(true);

        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(0, 0.5f);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}