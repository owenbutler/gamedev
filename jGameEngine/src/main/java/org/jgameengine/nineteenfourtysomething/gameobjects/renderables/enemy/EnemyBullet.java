package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * First simple enemy bullet.
 * 
 * @author Owen Butler
 */
public class EnemyBullet
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     */
    public EnemyBullet(float x, float y) {
        super(AssetConstants.gfx_enemyBullet, x, y, 13, 13);
        setScreenClipRemove(true);

        setCollidable(true);

        removeSelfIn(10.0f);

        setSortZ(GameConstants.Z_ENEMY_BULLET);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }


    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player) {
            removeSelf();
        }
    }
}