package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;
import org.owenbutler.grazier.constants.GameConstants;

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

        setVelY(GameConstants.PLAYER_BULLET_SPEED);

        setRotate(RandomUtils.nextInt(500) - 250);
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
        if (otherBody instanceof Enemy1) {
            removeSelf();
        }
    }

}