package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class MediumExplosion
        extends BaseDrawableGameObject {

    public MediumExplosion(float x, float y) {
        super(AssetConstants.gfx_explosion2, x, y, 64, 64);

        setRandomRotation();

        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(3, 0.2f);

        getSurface().enableAnimation(null, 4, 2);
        endFrame = 7;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.07f, 0.07f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });

    }


    public void think() {
        baseDrawableThink();
    }
}
