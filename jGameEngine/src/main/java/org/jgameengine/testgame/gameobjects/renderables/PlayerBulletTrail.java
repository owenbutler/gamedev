package org.jgameengine.testgame.gameobjects.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class PlayerBulletTrail
        extends BaseDrawableGameObject {

    public PlayerBulletTrail(float x, float y) {
        super("clouds/clouds.png", x, y, 16, 16);
        setScreenClipRemove(true);

        setRotation(RandomUtils.nextInt(255));

        final int rotationSpeed = 100;
        setRotate(RandomUtils.nextInt(rotationSpeed) - (rotationSpeed / 2));

        setScale(40, 40);

        setFadeAndRemove(2.5f);
    }


    public void think() {
        baseDrawableThink();
    }
}
