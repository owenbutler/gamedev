package org.jgameengine.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class Blood
        extends BaseDrawableGameObject {

    public Blood(float x, float y) {
        super(AssetConstants.gfx_blood, x, y, 1, 1);
        setScreenClipRemove(true);

        setFadeAndRemove(1.1f);

        setScale(1, 1);
    }

    public void think() {
        baseDrawableThink();
    }
}