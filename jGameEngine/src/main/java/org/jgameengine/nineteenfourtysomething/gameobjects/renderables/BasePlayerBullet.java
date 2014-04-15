package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.SmokePuff;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.VerySmallExplosion;

/**
 * Base class for player bullets.
 *
 * @author Owen Butler
 */
public abstract class BasePlayerBullet
        extends BaseDrawableGameObject {

    private int damage = 1;


    /**
     * constructor.
     *
     * @param x      x position
     * @param y      y position
     * @param velX   x velocity
     * @param velY   y velocity
     */
    protected BasePlayerBullet(float x, float y, float velX, float velY) {
        super(x, y);
        setVelXY(velX, velY);

        setScreenClipRemove(true);
        setCollidable(true);
    }

    protected void spawnSpark() {
        BulletSpark spark = new BulletSpark(getX(), getY());
        gameEngine.addGameObject(spark);

        spark.setVelX((velX/2.0f) + ( RandomUtils.nextInt(220) - 110));
        spark.setVelY((velY/2.0f) + ( RandomUtils.nextInt(220) - 110));
    }

    protected void spawnSmoke() {

        if (RandomUtils.nextBoolean()) {
            VerySmallExplosion smallExp = new VerySmallExplosion(this.x, this.y);
            gameEngine.addGameObject(smallExp);
        } else {
            SmokePuff smokePuff = new SmokePuff(x, y);
            gameEngine.addGameObject(smokePuff);
        }

    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }


    public int getDamage() {
        return damage;
    }


    public void setDamage(int damage) {
        this.damage = damage;
    }
}
