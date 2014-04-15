package org.owenbutler.theta.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;

/**
 * Abstract enemy base class so all enemies can be identified.
 */
public abstract class AbstractEnemyBase extends BaseDrawableGameObject {

    protected boolean dead;

    protected Player player;

    protected int health;


    public AbstractEnemyBase(String gfx, float x, float y, int width, int height) {
        super(gfx, x, y, width, height);

        player = (Player) gameEngine.getRegisteredGameService("player");
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof PlayerBullet) {
            takeDamage((PlayerBullet) otherBody);
        } else if (otherBody instanceof Player) {
            removeSelf();
        }
    }


    protected void takeDamage(PlayerBullet bulletThatHitMe) {

        showDamage(bulletThatHitMe);

        health--;

        if (health <= 0) {

            die();
        }

    }

    protected void die() {
        dead = true;
        removeSelf();
    }

    protected abstract void showDamage(PlayerBullet bulletThatHitMe);


    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getHealth() {
        return health;
    }


    public void setHealth(int health) {
        this.health = health;
    }
}
