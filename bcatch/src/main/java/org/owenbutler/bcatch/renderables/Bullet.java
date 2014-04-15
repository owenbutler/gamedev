package org.owenbutler.bcatch.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.bcatch.constants.AssetConstants;
import org.owenbutler.bcatch.constants.GameConstants;

public class Bullet
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public Bullet(float x, float y) {
        super(AssetConstants.gfx_bullet, x, y, 8, 8);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(4);

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

    public void fadeAway() {

        Player player = (Player) gameEngine.getRegisteredGameService("player");

        setVelXY(getVelocityFacing(player.getX(), player.getY(), GameConstants.BULLET_FADE_VEL));

        setScale(-15, -15);
        removeSelfIn(0.125f);
    }
}