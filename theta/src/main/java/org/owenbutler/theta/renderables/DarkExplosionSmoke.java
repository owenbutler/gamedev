package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.theta.constants.AssetConstants;

/**
 * A small graphic representing a dark explosion.
 *
 * @author Owen Butler
 */
public class DarkExplosionSmoke
        extends BaseDrawableGameObject {

    /**
     * create a new dark explosion smoke thing.
     *
     * @param x                 x position
     * @param y                 y position
     * @param delayScaleAndFade whether to delay setting scale params
     */
    public DarkExplosionSmoke(float x, float y, boolean delayScaleAndFade) {
        super(AssetConstants.gfx_darkExplosionSmoke, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(false);

        setSortZ(AssetConstants.Z_EXPLOSION_SMOKE);

        if (!delayScaleAndFade) {
            applyScaleAndFade();
        }

    }

    public DarkExplosionSmoke(float x, float y) {
        this(x, y, false);
    }

    private void applyScaleAndFade() {
        setScale(2200, 2200);
        setFadeAndRemove(0.22f);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

}