package org.owenbutler.goose.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.constants.GameConstants;

public class PlayerBullet extends BaseDrawableGameObject {

    /**
     * constructor.
     *
     * @param x x position
     * @param y y position
     */
    public PlayerBullet(float x, float y) {
        super(AssetConstants.gfx_playerBullet, x, y, 8, 16);

        setCollisionRadius(4);

        setCollidable(true);
        setScreenClipRemove(true);

        setVelY(GameConstants.PLAYER_BULLET_SPEED);

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
//        if (otherBody instanceof Player || otherBody instanceof Grazer) {
//            removeSelf();
//        }
    }

}
