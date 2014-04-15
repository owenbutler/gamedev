package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.apache.commons.lang.math.RandomUtils;

/**
 * User: Owen Butler
 * Date: 11/04/2005
 * Time: 17:12:46
 */
public class PlayerBulletTrail
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     */
    public PlayerBulletTrail(float x, float y) {
        super("clouds/clouds.png", x, y, 16, 16);
        setScreenClipRemove(true);

        setRotation(RandomUtils.nextInt(255));

        final int rotationSpeed = 100;
        setRotate(RandomUtils.nextInt(rotationSpeed)  - (rotationSpeed / 2));

        setScale(40, 40);

        setFadeAndRemove(2.5f);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }
}
