package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * detailed semi transperant explosion.
 *
 * @author Owen Butler
 */
public class TransExplosion
        extends BaseDrawableGameObject {

    /**
     * create a new detailed trans explosion.
     *
     * @param x    x position
     * @param y    y position
     */
    public TransExplosion(float x, float y) {
        super(AssetConstants.gfx_transExplosion, x, y, 64, 64);

        setRandomRotation();
        
        getSurface().enableAnimation(null, 8, 8);
        endFrame = 63;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.02f, 0.02f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });


        setSortZ(GameConstants.Z_EXPLOSION);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}
