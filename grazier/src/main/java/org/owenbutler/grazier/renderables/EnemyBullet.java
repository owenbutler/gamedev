package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class EnemyBullet
        extends BaseDrawableGameObject {

    public EnemyBullet(float x, float y) {
        super(AssetConstants.gfx_enemyBullet, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(false);
        setCollisionRadius(8);

        setRotate(RandomUtils.nextInt(500) - 250);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof Player || otherBody instanceof Grazer) {
            removeSelf();
        }
    }

    public boolean ignore(Collidable otherBody) {
        return otherBody instanceof EnemyBullet;
    }
}