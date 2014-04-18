package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.theta.constants.AssetConstants;

public class PlayerHitWave extends BaseDrawableGameObject {

    public PlayerHitWave(float x, float y) {
        super(AssetConstants.gfx_playerHitWave, x, y, 32, 32);

        setFadeAndRemove(0.12f);
        setScale(4200, 4200);

        setCollidable(true);
        setCollisionRadius(400);
        setSortZ(AssetConstants.Z_EXPLOSION_SMOKE);

        setScreenClipRemove(false);
    }

    public void think() {
        baseDrawableThink();
    }

}
