package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class VerySmallExplosion
        extends BaseDrawableGameObject {

    public VerySmallExplosion(float x, float y) {
        super(AssetConstants.gfx_smallExplosion, x, y, 16, 16);

        getSurface().enableAnimation(null, 4, 4);
        endFrame = 12;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.05f, 0.05f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });

        setRandomRotation();
    }

    public void think() {
        baseDrawableThink();
    }
}
