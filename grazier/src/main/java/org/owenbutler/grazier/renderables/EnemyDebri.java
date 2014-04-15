package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class EnemyDebri
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public EnemyDebri(float x, float y) {
        super(AssetConstants.gfx_enemyDebri, x, y, 16, 8);

        setScreenClipRemove(true);

        setCollidable(false);

        setRotate(RandomUtils.nextInt(500) - 250);

        setSortZ(AssetConstants.z_enemyDebri);
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