package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Really basic small explosion.
 *
 * @author Owen Butler
 */
public class SmallExplosion
        extends BaseDrawableGameObject {

    /**
     * create a new small explosion.
     *
     * @param x    x position
     * @param y    y position
     */
    public SmallExplosion(float x, float y) {
        super(AssetConstants.gfx_explosion1, x, y, 32, 32);

        setRotation((float) RandomUtils.nextInt(360));
        // set ourselves to start fading out in a second
        // fade for a second, then remove
        setFadeAndRemove(3, 0.2f);

        getSurface().enableAnimation(null, 4, 2);
        endFrame = 7;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.06f, 0.06f, new Event() {
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