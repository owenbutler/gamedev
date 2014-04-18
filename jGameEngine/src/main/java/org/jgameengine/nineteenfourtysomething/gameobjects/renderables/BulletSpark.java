package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class BulletSpark
        extends BaseDrawableGameObject {

    public BulletSpark(float x, float y) {
        super(AssetConstants.gfx_spark, x, y, 4, 4);
        setScreenClipRemove(true);

        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(0, 0.5f);
    }

    public void think() {
        baseDrawableThink();
    }
}