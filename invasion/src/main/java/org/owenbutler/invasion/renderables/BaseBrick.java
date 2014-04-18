package org.owenbutler.invasion.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.invasion.constants.AssetConstants;

public class BaseBrick
        extends BaseDrawableGameObject {

    public BaseBrick(float x, float y) {
        super(AssetConstants.gfx_baseBrick, x, y, 16, 16);
        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(6);

        setRandomRotation();
    }

    public void think() {
//        baseDrawableThink();
    }

    public void particleExplosion(int num) {
        // particle explosion
        for (int i = 0; i < num; ++i) {
            Particle particle = new Particle(x, y);
            particle.setVelXYRandom(20);
            gameEngine.addGameObject(particle);
        }

    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof BaseBrick) {
            return;
        }

        if (otherBody instanceof Enemy) {
            particleExplosion(5);
            removeSelf();
        } else if (otherBody instanceof Spaceman) {
            removeSelf();
        } else if (otherBody instanceof PlayerBullet1) {
            particleExplosion(10);
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