package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Background cloud.
 *
 * @author Owen Butler
 */
public class BackgroundCloud
        extends BaseDrawableGameObject {

    /**
     * create a new cloud.
     *
     * @param x    x position
     * @param y    y position
     */
    public BackgroundCloud(float x, float y) {
        super(AssetConstants.gfx_cloud, x, y, 2, 2);
        setScreenClipRemove(false);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

        // check if we are off the edge of the screen
        // if so, delete ourselves
        if (y > (480 + height)) {
            removeSelf();
        }
    }
}
