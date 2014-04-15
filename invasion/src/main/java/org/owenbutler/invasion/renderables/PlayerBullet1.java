package org.owenbutler.invasion.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.invasion.constants.AssetConstants;

public class PlayerBullet1
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public PlayerBullet1(float x, float y) {
        super(AssetConstants.gfx_playerBullet, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(5);

        gameEngine.getEventHandler().addEventInLoop(this, 0.05f, 0.05f, new Event() {
            public void trigger() {
                spawnParticle();
            }
        });
    }

    private void spawnParticle() {
        Particle particle = new Particle(x, y);
        particle.setVelXYWithRandomMod(new float[]{0, 0}, 10);
        gameEngine.addGameObject(particle);
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
        if (otherBody instanceof Enemy) {
            removeSelf();
        } else if (otherBody instanceof BaseBrick) {
            removeSelf();
        }
//        if (otherBody instanceof BaseAsteroid) {
//
//            BaseAsteroid aster = (BaseAsteroid) otherBody;
//
//            // spawn some sparks
//            int numSparks = ((BaseAsteroid) otherBody).getSmokes();
//            for (int i = 0; i < numSparks; ++i) {
//                spawnSpark();
//            }
//            removeSelf();
//
//            VerySmallExplosion explosion = new VerySmallExplosion(x, y);
//            explosion.setVelXY(aster.getVelX(), aster.getVelY());
//            gameEngine.addGameObject(explosion);
//        }
    }

}