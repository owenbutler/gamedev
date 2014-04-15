package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Small rock debri from an explosion.
 * 
 * @author Owen Butler
 */
public class SmallDebri
        extends BaseDrawableGameObject {

    /**
     * create a new debri.
     *
     * @param x    x position
     * @param y    y position
     */
    public SmallDebri(float x, float y) {
        super(AssetConstants.gfx_debri1, x, y, 16, 16);

        getSurface().enableAnimation(null, 4, 2);

        frame = RandomUtils.nextInt(5);
        getSurface().selectAnimationFrame(frame);
        endFrame = 5;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

        removeSelfIn(1.5f);
        
        setInitialRandomVelocity();

        setRandomRotation();

        setSortZ(GameConstants.Z_EXPLOSION_DEBRI);
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
