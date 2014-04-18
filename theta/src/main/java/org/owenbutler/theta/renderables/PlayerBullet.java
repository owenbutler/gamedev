package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.theta.constants.AssetConstants;

public class PlayerBullet
        extends BaseDrawableGameObject {

    public PlayerBullet(float x, float y) {
        super(AssetConstants.gfx_bullet, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(8);

        setSortZ(AssetConstants.Z_PLAYER_BULLET);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof AbstractEnemyBase) {
            removeSelf();
        }
    }

}