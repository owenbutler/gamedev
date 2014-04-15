package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.constants.GameConstants;

/**
 * background cloud.
 *
 * @author Owen Butler
 */
public class BackgroundCloud
        extends BaseDrawableGameObject {

    /**
     * create a new cloud sprite.
     *
     * @param level the level of the cloud
     */
    public BackgroundCloud(int level) {
        super(AssetConstants.gfx_cloud, 0, 0, GameConstants.CLOUD_WIDTH, GameConstants.CLOUD_HEIGHT);
        setScreenClipRemove(false);

        setCollidable(false);

        setVelY(GameConstants.CLOUD_VELOCITY + level);

        incrementHeight(level);
        incrementWidth(level);

        setRandomRotation();

        setSortZ(AssetConstants.Z_CLOUD + level);
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
//        if (otherBody instanceof Enemy1) {
//            removeSelf();
//        }
    }


}