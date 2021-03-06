package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.theta.constants.AssetConstants;

public class Strike
        extends BaseDrawableGameObject {

    public Strike(float x, float y) {
        super(AssetConstants.gfx_strike, x, y, 2, 16);
        setScreenClipRemove(true);

        setCollidable(false);

        setSortZ(AssetConstants.Z_STRIKE);

        setScale(0, 100);
        setFadeAndRemove(0.2f, 0.1f);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
    }

}