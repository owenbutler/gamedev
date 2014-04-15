package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.PlayerConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.BasePlayerBullet;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Missile fired by the player.
 *
 * @author Owen Butler
 */
public class Missile
        extends BasePlayerBullet {

    private int fireFacingX;

    private int fireFacingY;

    /**
     * create a new missile.
     *
     * @param x    x position
     * @param y    y position
     * @param facingX where the ship was facing when it fired the missile
     * @param facingY where the ship was facing when it fired the missile
     */
    public Missile(float x, float y, int facingX, int facingY) {
        super(x, y, 0, 0);

        fireFacingX = facingX;
        fireFacingY = facingY;

        setDamage(20);

        initSurface(AssetConstants.gfx_missiles, 16, 16);
        getSurface().enableAnimation(null, 4, 2);
        frame = RandomUtils.nextInt(8);
        getSurface().selectAnimationFrame(frame);

        // spawn some trails?
        gameEngine.getEventHandler().addEventInLoop(this, 0.02f, 0.02f, new Event() {
            public void trigger() {
                spawnTrail();
            }
        });

        gameEngine.getEventHandler().addEventInRepeat(this, 0.02f, 0.02f, 10, new Event() {
            public void trigger() {
                adjustVelocityTowardsOriginalFacing();
            }
        });
        calculateInitialVelocity();

        setScreenClipRemove(false);

        removeSelfIn(2.0f);
    }

    private void adjustVelocityTowardsOriginalFacing() {

        float faceVel[] = getVelocityFacing(fireFacingX, fireFacingY, PlayerConstants.PLAYER_MISSILE_SPEED);

        float diffX = velX - faceVel[0];
        float diffY = velY - faceVel[1];

        // adjust toward optimal velocity a bit
        velX -= Math.signum(diffX) * Math.min(100.0f, Math.abs(diffX));
        velY -= Math.signum(diffY) * Math.min(100.0f, Math.abs(diffY));

    }


    private void calculateInitialVelocity() {

        float[] xyVel = getVelocityFacing(fireFacingX, fireFacingY, PlayerConstants.PLAYER_MISSILE_SPEED);

        double angle = RandomUtils.nextDouble() * (Math.PI / 2);
        if (RandomUtils.nextBoolean()) {
            angle = -angle;
        }
        float velX2 = (float) (xyVel[0] * Math.cos(angle) - xyVel[1] * Math.sin(angle));
        float velY2 = (float) (xyVel[1] * Math.cos(angle) + xyVel[0] * Math.sin(angle));

        setVelXY(velX2, velY2);
        lookat(fireFacingX, fireFacingY);
    }


    private void spawnTrail() {

        SmokePuff smokePuff = new SmokePuff(x, y);
        smokePuff.setWidth(8);
        smokePuff.setHeight(8);
        gameEngine.addGameObject(smokePuff);
    }


    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof BaseEnemy) {
            TransExplosion explosion = new TransExplosion(x, y);
            gameEngine.addGameObject(explosion);

            removeSelf();
        }

    }

}
