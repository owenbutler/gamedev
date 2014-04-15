package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * wide shot player bullet.
 *
 * @author Owen Butler
 */
public class SmallPlayerBullet
        extends BasePlayerBullet {


    /**
     * create a new small player bullet.
     *
     * @param x    x position
     * @param y    y position
     * @param xVel x velocity
     * @param yVel y velocity
     */
    public SmallPlayerBullet(float x, float y, float xVel, float yVel) {
        super(x, y, xVel, yVel);

        initSurface(AssetConstants.gfx_playerBulletsThick, 16, 16);

        getSurface().enableAnimation(null, 4, 1);
        getSurface().selectAnimationFrame(RandomUtils.nextInt(4));

        setCollisionRadius(8.0f);

        setSortZ(GameConstants.Z_PLAYER_BULLET_SMALLTHICK);
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