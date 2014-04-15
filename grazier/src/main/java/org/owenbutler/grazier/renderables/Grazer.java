package org.owenbutler.grazier.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class Grazer
        extends BaseDrawableGameObject {

    protected Player player;

    protected boolean enabled;

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public Grazer(float x, float y) {
        super(AssetConstants.gfx_grazer, x, y, 90, 90);

        setCollidable(true);
        setCollisionRadius(45);

        setSortZ(AssetConstants.z_grazer);

        player = (Player) gameEngine.getRegisteredGameService("player");
    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        x = player.getX();
        y = player.getY();

        baseDrawableThink();
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof EnemyBullet) {

            EnemyBullet bullet = (EnemyBullet) otherBody;

            EnemyBulletGrazed newBulletGrazed = new EnemyBulletGrazed(bullet.getX(), bullet.getY());
            newBulletGrazed.setVelXY(bullet.getVelX(), bullet.getVelY());
            gameEngine.addGameObject(newBulletGrazed);

            player.grazedABullet();
        }
    }

    public void enable() {

        enabled = true;

        setBlRender(true);

    }

    public void disable() {
        enabled = false;
        setBlRender(false);
    }
}