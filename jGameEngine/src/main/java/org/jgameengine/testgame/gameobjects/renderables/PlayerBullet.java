package org.jgameengine.testgame.gameobjects.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;


public class PlayerBullet
        extends BaseDrawableGameObject {

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
        spark.setVelX((RandomUtils.nextFloat() * (float) sparkRandomVel) - (float) (sparkRandomVel / 2));
        spark.setVelY((RandomUtils.nextFloat() * (float) sparkRandomVel) - (float) (sparkRandomVel / 2));
    }

    public void think() {
        baseDrawableThink();
    }

    void spawnParticle() {
        gameEngine.addGameObject(new PlayerBulletTrail(getX(), getY()));
    }


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
