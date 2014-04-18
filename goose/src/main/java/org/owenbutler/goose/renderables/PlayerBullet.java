package org.owenbutler.goose.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.constants.GameConstants;

public class PlayerBullet extends BaseDrawableGameObject {

    public PlayerBullet(float x, float y) {
        super(AssetConstants.gfx_playerBullet, x, y, 8, 16);

        setCollisionRadius(4);

        setCollidable(true);
        setScreenClipRemove(true);

        setVelY(GameConstants.PLAYER_BULLET_SPEED);

        setSortZ(AssetConstants.Z_PLAYER_BULLET);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
//        if (otherBody instanceof Player || otherBody instanceof Grazer) {
//            removeSelf();
//        }
    }

}
