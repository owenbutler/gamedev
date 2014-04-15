package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.theta.constants.AssetConstants;

public class PlayerBullet
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public PlayerBullet(float x, float y) {
        super(AssetConstants.gfx_bullet, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(8);

        setSortZ(AssetConstants.Z_PLAYER_BULLET);
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
        if (otherBody instanceof AbstractEnemyBase) {
            removeSelf();
        }
    }

}