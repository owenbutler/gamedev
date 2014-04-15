package org.owenbutler.planetesimal.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.planetesimal.constants.AssetConstants;

/**
 * Very Small explosion.
 *
 * @author Owen Butler
 */
public class VerySmallExplosion
        extends BaseDrawableGameObject {

    /**
     * create a new very small explosion.
     *
     * @param x    x position
     * @param y    y position
     */
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


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}