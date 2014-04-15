package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class EnemyBulletGrazed
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public EnemyBulletGrazed(float x, float y) {
        super(AssetConstants.gfx_enemyBulletGrazed, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(false);
        setCollisionRadius(8);

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
        if (otherBody instanceof Player) {
            removeSelf();
        }
    }

}