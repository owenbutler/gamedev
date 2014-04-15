package org.owenbutler.invasion.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.invasion.constants.AssetConstants;
import org.owenbutler.invasion.constants.GameConstants;

public class Blood
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x x position
     * @param y y position
     */
    public Blood(float x, float y) {
        super(AssetConstants.gfx_blood, x, y, 1, 1);
        setScreenClipRemove(true);

        // fade for 1/2 a second, then remove
        setFadeAndRemove(1.1f);
//        removeSelfIn(0.5f);

        setSortZ(GameConstants.Z_PARTICLE);

        setScale(1, 1);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}