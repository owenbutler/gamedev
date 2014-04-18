package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class SmallSmoke
        extends BaseDrawableGameObject {

    public SmallSmoke(float x, float y) {
        super(AssetConstants.gfx_smallSmoke, x, y, 1, 1);

        setVelXY(RandomUtils.nextInt(5), RandomUtils.nextInt(5));

        // set ourselves to start fading out in a second
        // fade for a 1/2 second, then remove
        setFadeAndRemove(0.5f, 0.5f);

        setSortZ(GameConstants.Z_EXPLOSION_SMOKE);
    }


    public void think() {
        baseDrawableThink();
    }
}
