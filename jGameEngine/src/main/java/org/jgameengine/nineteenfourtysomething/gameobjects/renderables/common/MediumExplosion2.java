package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * medium explosion.
 *
 * @author Owen Butler
 */
public class MediumExplosion2
        extends BaseDrawableGameObject {

    /**
     * create a medium explosion.
     *
     * @param x    x position
     * @param y    y position
     */
    public MediumExplosion2(float x, float y) {
        super(AssetConstants.gfx_mediumExplosion, x, y, 32, 32);

        getSurface().enableAnimation(null, 4, 4);

        frame = RandomUtils.nextInt(5);
        getSurface().selectAnimationFrame(frame);
        endFrame = 15;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.05f, 0.05f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });
    }


    public void setInitialRandomVelocity() {
        velX = RandomUtils.nextInt(200) - 100;
        velY = RandomUtils.nextInt(200) - 100;
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}
