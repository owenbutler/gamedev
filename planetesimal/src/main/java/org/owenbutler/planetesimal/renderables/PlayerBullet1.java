package org.owenbutler.planetesimal.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.planetesimal.constants.AssetConstants;

public class PlayerBullet1
        extends BaseDrawableGameObject {

    public PlayerBullet1(float x, float y) {
        super(AssetConstants.gfx_playerBullet1, x, y, 16, 16);
        setScreenClipRemove(true);

        setCollidable(true);
        setCollisionRadius(5);
    }

    public void think() {
        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof BaseAsteroid) {

            BaseAsteroid aster = (BaseAsteroid) otherBody;

            // spawn some sparks
            int numSparks = ((BaseAsteroid) otherBody).getSmokes();
            for (int i = 0; i < numSparks; ++i) {
                spawnSpark();
            }
            removeSelf();

            VerySmallExplosion explosion = new VerySmallExplosion(x, y);
            explosion.setVelXY(aster.getVelX(), aster.getVelY());
            gameEngine.addGameObject(explosion);
        }
    }

    protected void spawnSpark() {
        SmokePuff spark = new SmokePuff(getX(), getY());
        gameEngine.addGameObject(spark);

        spark.setVelX(-(velX / 4.0f) + (RandomUtils.nextInt(50) - 25));
        spark.setVelY(-(velY / 4.0f) + (RandomUtils.nextInt(50) - 25));
    }

}