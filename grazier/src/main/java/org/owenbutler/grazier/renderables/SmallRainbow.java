package org.owenbutler.grazier.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class SmallRainbow
        extends BaseDrawableGameObject {

    public SmallRainbow(float x, float y) {
        super(AssetConstants.gfx_smallRainbow, x, y, 32, 32);

        setCollidable(false);

        setSortZ(AssetConstants.z_rainbow);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
    }

}