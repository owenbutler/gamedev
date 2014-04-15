package org.owenbutler.warpball.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.warpball.constants.AssetConstants;
import org.owenbutler.warpball.constants.GameConstants;

/**
 * The enemy paddle in our ponglike game.
 *
 * @author Owen Butler
 */
public class EnemyPaddle
        extends BaseDrawableGameObject {

    /**
     * constructor.
     *
     * @param x           x position
     * @param y           y position
     */
    public EnemyPaddle(float x, float y) {
        super(AssetConstants.gfx_paddle, x, y, 32, 128);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {

        // find ball
        Ball ball = (Ball) gameEngine.getRegisteredGameService("ball");

        // find direction of ball compared to us
        float diff = ball.getY() - y;
        

        // move towards ball
        setVelY(Math.signum(diff) * GameConstants.ENEMY_SPEED);

        clipY();

        baseDrawableThink();
    }


    private void clipY() {
        if (y < 64) {
            setY(64);
        } else if (y > 600 - 64) {
            setY(600 - 64);
        }
    }
}