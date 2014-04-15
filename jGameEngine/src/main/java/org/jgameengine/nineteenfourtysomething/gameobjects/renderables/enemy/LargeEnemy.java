package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.TransExplosion;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * Common large enemy functionality.
 *
 * @author Owen Butler
 */
public class LargeEnemy
        extends BaseEnemy {

    /**
     * Derived enemy ships should do logic here.
     */
    protected void enemyThink() {

    }

    /**
     * Entry point for enemies to explode.
     * <p/>
     * By default, we spawn a medium explosion.
     */
    protected void explode() {

        for (int i = 0; i < 6; ++i) {

            float xPos = x + (RandomUtils.nextInt(60) - 30);
            float yPos = y + (RandomUtils.nextInt(60) - 30);
            TransExplosion transExplosion = new TransExplosion(xPos, yPos);
            transExplosion.setScale(20, 20);

            gameEngine.addGameObject(transExplosion);
        }

        spawnDebri();
    }

    /**
     * Helper method to be used to init a large enemy.
     */
    protected void initLargeEnemy() {
        width = EnemyShipConstants.LARGE_SHIP_WIDTH;
        height = EnemyShipConstants.LARGE_SHIP_HEIGHT;
        surface.initSurface(AssetConstants.gfx_largeShips, x, y, EnemyShipConstants.LARGE_SHIP_WIDTH, EnemyShipConstants.LARGE_SHIP_HEIGHT);

        getSurface().enableAnimation(null, 2, 2);

        setCollidable(true);
        setCollisionRadius(EnemyShipConstants.LARGE_SHIP_COLLISION_RADIUS);

        initWaypointTracking();

        initLargeEnemyShooting();
    }

    /**
     * Initialise how the large enemy shoots.
     */
    protected void initLargeEnemyShooting() {

        shootSequenceType = RandomUtils.nextInt(3);
        playedShootSequenceStartSound = false;
        float randomWait = RandomUtils.nextFloat() * 5;
        nextShootSequenceStartTime = gameEngine.getCurrentTime() + randomWait;

        gameEngine.getEventHandler().addEventIn(this, randomWait, new Event() {
            public void trigger() {
                checkLargeEnemyShoot();
            }
        });

    }

    /**
     * Check whether the large enemy should shoot.
     * <p/>
     * This method is currently only called by periodic events set up either inside it, or inside the init.
     */
    protected void checkLargeEnemyShoot() {

        float time = gameEngine.getCurrentTime();
        Event event = null;
        float nextCheckInterval = 0.0f;

        switch (shootSequenceType) {
            case EnemyShipConstants.SHOOT_SEQUENCE_LONG:
                // check if we still should shoot
                if (time > nextShootSequenceStartTime + EnemyShipConstants.SHOOT_SEQUENCE_LONG_LENGTH) {
                    initLargeEnemyShooting();
                    return;
                }

                // set event to shoot again if so
                event = new Event() {
                    public void trigger() {
                        checkLargeEnemyShoot();
                    }
                };
                nextCheckInterval = EnemyShipConstants.SHOOT_SEQUENCE_LONG_INTERVAL;

                // shoot
                shootSimple();
                if (!offScreen()) {
                    gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGun).playSample();
                }
                break;
            case EnemyShipConstants.SHOOT_SEQUENCE_WIDE:
                // check if we still should shoot
                if (time > nextShootSequenceStartTime + EnemyShipConstants.SHOOT_SEQUENCE_WIDE_LENGTH) {
                    initLargeEnemyShooting();
                    return;
                }

                // set event to shoot again if so
                event = new Event() {
                    public void trigger() {
                        checkLargeEnemyShoot();
                    }
                };
                nextCheckInterval = EnemyShipConstants.SHOOT_SEQUENCE_WIDE_INTERVAL;

                // shoot
                shootStraightAtPlayer();
                if (!offScreen()) {
                    gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGun).playSample();
                }
                break;
            case EnemyShipConstants.SHOOT_SEQUENCE_FAT:
                // check if we still should shoot
                if (time > nextShootSequenceStartTime + EnemyShipConstants.SHOOT_SEQUENCE_FAT_LENGTH) {
                    initLargeEnemyShooting();
                    return;
                }

                // set event to shoot again if so
                event = new Event() {
                    public void trigger() {
                        checkLargeEnemyShoot();
                    }
                };
                nextCheckInterval = EnemyShipConstants.SHOOT_SEQUENCE_FAT_INTERVAL;

                // shoot
                shootSimple();
                if (!offScreen()) {
                    if (!playedShootSequenceStartSound) {
                        playedShootSequenceStartSound = true;
                        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemySpamGun).playSample();
                    }
                }
                break;
            default:
                break;
        }

        gameEngine.getEventHandler().addEventIn(this, nextCheckInterval, event);
    }

}
