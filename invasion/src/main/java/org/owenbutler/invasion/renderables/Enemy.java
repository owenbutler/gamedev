package org.owenbutler.invasion.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.invasion.constants.AssetConstants;
import org.owenbutler.invasion.constants.GameConstants;

public class Enemy extends BaseDrawableGameObject {

    protected int health;

    public Enemy() {
        super(AssetConstants.gfx_enemy, 0, 0, 32.0f, 32.0f);

        setCollidable(true);
        setCollisionRadius(GameConstants.ENEMY_COLLISION_RADIUS);
        setScreenClipRemove(false);

        surface.enableAnimation(null, 2, 2);
        surface.selectAnimationFrame(RandomUtils.nextInt(3));

        setXY(20, -20);

        moveDownThenRight();

        health = GameConstants.ENEMY_HEALTH;
    }

    private void moveDownThenRight() {

        moveDown();

        gameEngine.getEventHandler().addEventIn(this, GameConstants.ENEMY_MOVE_DOWN_TIME, new Event() {
            public void trigger() {

                moveRight();
            }
        });
    }

    private void moveDown() {
        setVelXY(0.0f, GameConstants.ENEMY_VELY);
    }

    private void moveRight() {

        setVelXY(GameConstants.ENEMY_VELX, 0.0f);

        gameEngine.getEventHandler().addEventIn(this, GameConstants.ENEMY_MOVE_ACROSS_TIME, new Event() {
            public void trigger() {

                moveDownThenLeft();
            }
        });
    }

    private void moveDownThenLeft() {
        moveDown();

        gameEngine.getEventHandler().addEventIn(this, GameConstants.ENEMY_MOVE_DOWN_TIME, new Event() {
            public void trigger() {

                moveLeft();
            }
        });
    }

    private void moveLeft() {

        setVelXY(-GameConstants.ENEMY_VELX, 0.0f);

        gameEngine.getEventHandler().addEventIn(this, GameConstants.ENEMY_MOVE_ACROSS_TIME, new Event() {
            public void trigger() {

                moveDownThenRight();
            }
        });
    }

    public void think() {
        baseDrawableThink();

    }

    public void collision(Collidable otherBody) {

        if (otherBody instanceof PlayerBullet1) {

            health--;

            // particle explosion
            for (int i = 0; i < 10; ++i) {
                Particle particle = new Particle(x, y);
                particle.setVelXYRandom(50);
                gameEngine.addGameObject(particle);
            }

            if (health == 0) {
                // explode...

                // spawn poor spaceman
                Spaceman spaceman = new Spaceman(x, y);
                gameEngine.addGameObject(spaceman);

                removeSelf();
            }
        }
    }
}
