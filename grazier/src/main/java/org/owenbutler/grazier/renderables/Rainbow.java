package org.owenbutler.grazier.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.grazier.constants.AssetConstants;

public class Rainbow
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public Rainbow(float x, float y) {
        super(AssetConstants.gfx_rainbow, 400, 300, 800, 600);

        setCollidable(false);

        setSortZ(AssetConstants.z_rainbow);

        setFadeAndRemove(0.3f);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

}