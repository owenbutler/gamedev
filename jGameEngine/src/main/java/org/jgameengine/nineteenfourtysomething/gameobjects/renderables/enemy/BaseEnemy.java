package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.BasePlayerBullet;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.Player;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.BaseDrawableGameObject194x;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.MidExplosion;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.SmallSmoke;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;
import org.jgameengine.nineteenfourtysomething.logic.WarpManager;
import org.jgameengine.renderer.Surface2dFactory;

abstract public class BaseEnemy
        extends BaseDrawableGameObject194x {

    protected int health = EnemyShipConstants.DEFAULT_HEALTH;

    protected int[] currentWaypoint;

    protected float nextShootSequenceStartTime;

    protected int shootSequenceType;

    protected boolean playedShootSequenceStartSound;

    protected boolean giveBonus = true;

    protected boolean hitSoundThisFrame = false;

    public BaseEnemy() {
        super();
        setScreenClipRemove(false);

        int[] spawnPoint = getNextSpawnPoint();

        this.x = spawnPoint[0];
        this.y = spawnPoint[1];

        surface = Surface2dFactory.getSurface2d();
        setNextWaypoint();

        setSortZ(GameConstants.Z_ENEMY);

    }

    public void think() {

        baseDrawableThink();
        enemyThink();

        hitSoundThisFrame = false;
    }

    protected abstract void enemyThink();

    public void damage(int damage) {
        if (health <= 0) {
            return;
        }

        health -= damage;

        if (health <= 0) {
            killed();
        } else {
            if (!hitSoundThisFrame) {
                hitSoundThisFrame = true;
                gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyHit).playSample();
            }
        }
    }

    public void killed() {

        String soundFile = null;
        explode();
        if (isGiveBonus()) {
            giveBonus();
            int sound = RandomUtils.nextInt(3);
            switch (sound) {
                case 0:
                    soundFile = AssetConstants.snd_explosion0;
                    break;
                case 1:
                    soundFile = AssetConstants.snd_explosion1;
                    break;
                case 2:
                    soundFile = AssetConstants.snd_explosion2;
                    break;
                default:
                    break;
            }
            gameEngine.getAudioManager().loadOrGetSample(soundFile).playSample();

            ((WarpManager) gameEngine.getRegisteredGameService("warpManager")).addWarp((int) x, (int) y);
        }

        removeSelf();
    }

    protected void explode() {
        MidExplosion exp = new MidExplosion(x, y);
        gameEngine.addGameObject(exp);

        spawnDebri();
    }

    protected void giveBonus() {
    }

    private int[] getNextSpawnPoint() {
        int x, y;

        x = RandomUtils.nextInt(640);
//        y = RandomUtils.nextInt(480);
        y = -100 - RandomUtils.nextInt(100);

        return new int[]{x, y};
    }

    private int[] getSpawnSmall() {
        int x, y;

        if (RandomUtils.nextBoolean()) {
            x = 700;
        } else {
            x = -60;
        }

        y = RandomUtils.nextInt(400);

        currentWaypoint = new int[]{x, y};

        return new int[]{x, y};
    }

    protected void setNextWaypoint() {
        currentWaypoint = new int[]{RandomUtils.nextInt(600) + 20, RandomUtils.nextInt(310) + 60};
    }

    protected void setNextWaypointSmall() {
        int x, y;
        if (currentWaypoint[0] == -60) {
            x = 700;
        } else {
            x = -60;
        }

        y = RandomUtils.nextInt(400);

        currentWaypoint = new int[]{x, y};
    }

    protected boolean reachedCurrentWaypoint() {
        int xDistance = Math.abs(((int) x) - currentWaypoint[0]);
        if (xDistance < EnemyShipConstants.WAYPOINT_VARIANCE) {
            int yDistance = Math.abs(((int) y) - currentWaypoint[1]);
            if (yDistance < EnemyShipConstants.WAYPOINT_VARIANCE) {
                return true;
            }
        }
        return false;
    }

    protected void initSmallEnemy() {
        width = EnemyShipConstants.SMALL_SHIP_WIDTH;
        height = EnemyShipConstants.SMALL_SHIP_HEIGHT;
        surface.initSurface(AssetConstants.gfx_enemySmall, x, y, EnemyShipConstants.SMALL_SHIP_WIDTH, EnemyShipConstants.SMALL_SHIP_HEIGHT);

        getSurface().enableAnimation(null, 8, 4);

        setCollidable(true);
        setCollisionRadius(EnemyShipConstants.SMALL_SHIP_COLLISION_RADIUS);

        int[] spawn = getSpawnSmall();
        this.x = spawn[0];
        this.y = spawn[1];

        setNextWaypointSmall();
        initWaypointTrackingSmall();
        getSurface().lookAt(currentWaypoint[0], currentWaypoint[1]);

        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.02f, new Event() {
            public void trigger() {
                spawnSmoke();
            }
        });
    }

    protected void initMediumEnemy() {
        width = EnemyShipConstants.MEDIUM_SHIP_WIDTH;
        height = EnemyShipConstants.MEDIUM_SHIP_HEIGHT;
        surface.initSurface(AssetConstants.gfx_mediumShips, x, y, EnemyShipConstants.MEDIUM_SHIP_WIDTH, EnemyShipConstants.MEDIUM_SHIP_HEIGHT);

        getSurface().enableAnimation(null, 4, 2);

        setCollidable(true);
        setCollisionRadius(EnemyShipConstants.MEDIUM_SHIP_COLLISION_RADIUS);

        initWaypointTracking();

        initMediumEnemyShooting();
    }

    protected void initMediumEnemyShooting() {

        shootSequenceType = RandomUtils.nextInt(2);
        float randomWait = RandomUtils.nextFloat() * 5 + 2;
        nextShootSequenceStartTime = gameEngine.getCurrentTime() + randomWait;

        gameEngine.getEventHandler().addEventIn(this, randomWait, new Event() {
            public void trigger() {
                checkMediumEnemyShoot();
            }
        });
    }

    protected void checkMediumEnemyShoot() {
        float time = gameEngine.getCurrentTime();
        Event event = null;
        float nextCheckInterval = 0.0f;

        switch (shootSequenceType) {
            case EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_WIDE:
                // check if we still should shoot
                if (time > nextShootSequenceStartTime + EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_WIDE_LENGTH) {
                    initMediumEnemyShooting();
                    return;
                }

                // set event to shoot again if so
                event = new Event() {
                    public void trigger() {
                        checkMediumEnemyShoot();
                    }
                };
                nextCheckInterval = EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_WIDE_INTERVAL;

                // shoot
                shootSimple();
                break;
            case EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_STRAIGHT:
                // check if we still should shoot
                if (time > nextShootSequenceStartTime + EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_STRAIGHT_LENGTH) {
                    initMediumEnemyShooting();
                    return;
                }

                // set event to shoot again if so
                event = new Event() {
                    public void trigger() {
                        checkMediumEnemyShoot();
                    }
                };
                nextCheckInterval = EnemyShipConstants.SHOOT_SEQUENCE_MEDIUM_STRAIGHT_INTERVAL;

                // shoot
                shootStraightAtPlayer();
                break;
            default:
                break;
        }

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGunMedium).playSample();
        gameEngine.getEventHandler().addEventIn(this, nextCheckInterval, event);
    }

    public void initEnemyAnimation() {
        getSurface().selectAnimationFrame(frame);
        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });
    }

    protected void initWaypointTracking() {

        gameEngine.getEventHandler().addEventIn(this, EnemyShipConstants.WAYPOINT_TRACK_TIME, new Event() {
            public void trigger() {
                checkWayPoint();
            }
        });
    }

    protected void initWaypointTrackingSmall() {

        gameEngine.getEventHandler().addEventIn(this, EnemyShipConstants.WAYPOINT_TRACK_TIME + RandomUtils.nextFloat() * 10, new Event() {
            public void trigger() {
                checkWayPointSmall();
            }
        });
    }

    private void checkWayPoint() {

        float nextTrackTime = EnemyShipConstants.WAYPOINT_TRACK_TIME;

        // check if we have arrived at our waypoint
        if (reachedCurrentWaypoint()) {
            // if so, set a new waypoint, wait a bit
            // stop moving
//            setVelX(0.0f);
//            setVelY(0.0f);

            setNextWaypoint();

//            nextTrackTime = 0.5f;
        } else {

            float accelFactor = 0.1f;
            float xDiff = (float) currentWaypoint[0] - x;
            float yDiff = (float) currentWaypoint[1] - y;
            velX = capSpeed(velX + (xDiff * accelFactor));
            velY = capSpeed(velY + (yDiff * accelFactor));

//            setVelX(capSpeed((float) currentWaypoint[0] - x));
//            setVelY(capSpeed((float) currentWaypoint[1] - y));
        }

        // if not, move towards it, and check again in a bit
        gameEngine.getEventHandler().addEventIn(this, nextTrackTime, new Event() {
            public void trigger() {
                checkWayPoint();
            }
        });
    }

    private void checkWayPointSmall() {

        float nextTrackTime = EnemyShipConstants.WAYPOINT_TRACK_TIME;
        getSurface().lookAt(currentWaypoint[0], currentWaypoint[1]);

        // check if we have arrived at our waypoint
        if (reachedCurrentWaypoint()) {
            // if so, set a new waypoint, wait a bit
            // stop moving
            setVelX(0.0f);
            setVelY(0.0f);

            setNextWaypointSmall();

            nextTrackTime = 1.5f;
        } else {
            setVelXY(getVelocityFacing(currentWaypoint[0], currentWaypoint[1], EnemyShipConstants.SMALL_ENEMY_TOP_SPEED));
        }

        // if not, move towards it, and check again in a bit
        gameEngine.getEventHandler().addEventIn(this, nextTrackTime, new Event() {
            public void trigger() {
                checkWayPointSmall();
            }
        });
    }

    private float capSpeed(float v) {
        float sign = Math.signum(v);

        float absV = Math.abs(v);

        if (absV > EnemyShipConstants.ENEMY_TOP_SPEED) {
            return EnemyShipConstants.ENEMY_TOP_SPEED * sign;
        }

        return v;
    }

    public void collision(Collidable otherBody) {

        if (otherBody instanceof BasePlayerBullet) {

            damage(((BasePlayerBullet) otherBody).getDamage());
        }
    }

    public void shootWithModifyer(int xVariance, int yVariance) {

        if (offScreen()) {
            return;
        }

        // find where the player is at
        Player player = (Player) gameEngine.getRegisteredGameService("player");
        float playerX = player.getX() + xVariance;
        float playerY = player.getY() + yVariance;

        // create the bullet and fire away
        EnemyBullet bullet = new EnemyBullet(this.x, this.y);
        bullet.setVelXY(getVelocityFacing(playerX, playerY, EnemyShipConstants.SIMPLE_BULLET_SPEED));
        gameEngine.addGameObject(bullet);
    }

    public void shootSimple() {
        shootWithModifyer(RandomUtils.nextInt(180) - 90, RandomUtils.nextInt(180) - 90);
    }

    public void shootStraightAtPlayer() {
        shootWithModifyer(0, 0);
    }

    private void spawnSmoke() {
        gameEngine.addGameObject(new SmallSmoke(x, y));
    }

    public boolean isGiveBonus() {
        return giveBonus;
    }

    public void setGiveBonus(boolean giveBonus) {
        this.giveBonus = giveBonus;
    }

}
