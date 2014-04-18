package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;
import org.owenbutler.grazier.constants.GameConstants;

public class PlayerBullet
        extends BaseDrawableGameObject {

    public PlayerBullet(float x, float y) {
        super(AssetConstants.gfx_bullet, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(8);

        setVelY(GameConstants.PLAYER_BULLET_SPEED);

        setRotate(RandomUtils.nextInt(500) - 250);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof Enemy1) {
            removeSelf();
        }
    }

}