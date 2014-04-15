package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Straight shot player bullet.
 *
 * @author Owen Butler
 */
public class PlayerBullet
        extends BasePlayerBullet {

    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     * @param xVel x velocity
     * @param yVel y velocity
     */
    public PlayerBullet(float x, float y, float xVel, float yVel) {
        super(x, y, xVel, yVel);

        initSurface(AssetConstants.gfx_playerBullet, 32, 32);

        setCollisionRadius(8.0f);

        setSortZ(GameConstants.Z_PLAYER_BULLET);
    }


    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof BaseEnemy) {
            // spawn some sparks
            int numSparks = 7;
            for (int i = 0; i < numSparks; ++i) {
                spawnSpark();
            }
            spawnSmoke();
            removeSelf();
        }

    }

}
