package org.owenbutler.invasion.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.invasion.constants.AssetConstants;
import org.owenbutler.invasion.constants.GameConstants;

public class Particle
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x x position
     * @param y y position
     */
    public Particle(float x, float y) {
        super(AssetConstants.gfx_particle, x, y, 1, 1);
        setScreenClipRemove(true);

        // fade for 1/2 a second, then remove
        setFadeAndRemove(0.6f);
//        removeSelfIn(0.5f);

        setSortZ(GameConstants.Z_PARTICLE);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}