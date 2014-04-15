package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Mid sized explosion.
 * 
 * @author Owen Butler
 */
public class MidExplosion extends BaseDrawableGameObject {

    /**
     * create a new medium explosion.
     *
     * @param x    x position
     * @param y    y position
     */
    public MidExplosion(float x, float y) {
        super(AssetConstants.gfx_explosion3, x, y, 32, 32);

        setRotation((float) RandomUtils.nextInt(360));

        getSurface().enableAnimation(null, 8, 2);
        endFrame = 11;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.05f, 0.05f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });

    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}