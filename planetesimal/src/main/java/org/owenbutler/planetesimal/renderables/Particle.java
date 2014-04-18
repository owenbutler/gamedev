package org.owenbutler.planetesimal.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;

public class Particle
        extends BaseDrawableGameObject {

    public Particle(float x, float y) {
        super(AssetConstants.gfx_particle, x, y, 1, 1);
        setScreenClipRemove(true);

        // fade for 1/2 a second, then remove
        setFadeAndRemove(0.6f);
//        removeSelfIn(0.5f);

        setSortZ(GameConstants.Z_PARTICLE);
    }

    public void think() {
        baseDrawableThink();
    }
}