package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class BackgroundCloud
        extends BaseDrawableGameObject {

    public BackgroundCloud(float x, float y) {
        super(AssetConstants.gfx_cloud, x, y, 2, 2);
        setScreenClipRemove(false);
    }


    public void think() {
        baseDrawableThink();

        // check if we are off the edge of the screen
        // if so, delete ourselves
        if (y > (480 + height)) {
            removeSelf();
        }
    }
}
