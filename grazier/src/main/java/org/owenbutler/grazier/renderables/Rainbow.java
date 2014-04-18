package org.owenbutler.grazier.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.grazier.constants.AssetConstants;

public class Rainbow
        extends BaseDrawableGameObject {

    public Rainbow(float x, float y) {
        super(AssetConstants.gfx_rainbow, 400, 300, 800, 600);

        setCollidable(false);

        setSortZ(AssetConstants.z_rainbow);

        setFadeAndRemove(0.3f);
    }

    public void think() {
        baseDrawableThink();
    }

}