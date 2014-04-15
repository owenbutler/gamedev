package org.owenbutler.bcatch.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.bcatch.constants.AssetConstants;

public class BulletCatcher
        extends BaseDrawableGameObject {

    protected Player owner;

    protected int numCaughtBullets;

    /**
     * create a new player bullet 1.
     *
     * @param x        x position
     * @param y        y position
     * @param theOwner the object owner
     */
    public BulletCatcher(float x, float y, Player theOwner) {
        super(AssetConstants.gfx_bulletCatcher, x, y, 128, 128);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(64);

        owner = theOwner;
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        setX(owner.getX());
        setY(owner.getY());

        baseDrawableThink();
    }

    private void caughtABullet(Bullet bullet) {

        numCaughtBullets++;

//        ReboundBullet reboundBullet = new ReboundBullet(bullet.getX(), bullet.getY());
//        caughtBullets.add(reboundBullet);
//        reboundBullet.setVelXYRandom(10);
//
//
//        gameEngine.addGameObject(reboundBullet);
//
//        gameEngine.removeGameObject(bullet);

        bullet.setCollidable(false);
        bullet.fadeAway();
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof Bullet) {
            caughtABullet((Bullet) otherBody);
        }
    }

    public void releaseBullets() {

        // notify the player how many bullets caught
        owner.unleashBullets(numCaughtBullets);
        numCaughtBullets = 0;
    }
}