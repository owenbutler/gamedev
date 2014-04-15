package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.common.events.Event;
import org.apache.commons.lang.math.RandomUtils;

/**
 * User: Owen Butler
 * Date: 11/04/2005
 * Time: 17:12:46
 */
public class PlayerBullet
        extends BaseDrawableGameObject {

//    private static final Logger logger = Logger.getLogger(PlayerBullet.class);

    /**
     * create a new player bullet.
     *
     * @param x    x position
     * @param y    y position
     * @param xVel x velocity
     * @param yVel y velocity
     */
    public PlayerBullet(float x, float y, float xVel, float yVel) {
        super("playerBullet.png", x, y, 16, 32);

        setVelX(xVel);
        setVelY(yVel);

        setScreenClipRemove(true);

        // set a looping event to spawn a trail
        gameEngine.getEventHandler().addEventInLoop(this, 0.09f, 0.15f, new Event() {
            public void trigger() {
                spawnParticle();
            }
        });

        // set a looping event to spawn some sparks!
        gameEngine.getEventHandler().addEventInLoop(this, 0.02f, 0.04f, new Event() {
            public void trigger() {
                spawnSpark();
            }
        });

        setCollidable(true);
        setCollisionRadius(8.0f);
    }


    private void spawnSpark() {
        Spark spark = new Spark(getX(), getY());
        gameEngine.addGameObject(spark);

        final int sparkRandomVel = 140;
        spark.setVelX( (RandomUtils.nextFloat() * (float)sparkRandomVel) - (float)(sparkRandomVel / 2) );
        spark.setVelY( (RandomUtils.nextFloat() * (float)sparkRandomVel) - (float)(sparkRandomVel / 2) );
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

    void spawnParticle() {
        gameEngine.addGameObject(new PlayerBulletTrail(getX(), getY()));
    }


    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof PlayerBullet) {
            return;
        }
        
        if (isOwner(otherBody)) {
            return;
        }

        // spawn some sparks
        int numSparks = 50;
        for (int i = 0; i < numSparks; ++i) {
            spawnSpark();
        }
        removeSelf();
    }
}
