package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.input.MouseListener;
import org.jgameengine.input.PauseListener;
import org.jgameengine.nineteenfourtysomething.constants.PlayerConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Missile;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Powerup;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.SmallDebri;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.TransExplosion;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.EnemyBullet;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;
import org.jgameengine.nineteenfourtysomething.logic.DefaultGameFlowLogic;

/**
 * The basic player object of the world.
 * <p/>
 * The hero of the story, the one who fights all the bad guys etc.. etc..
 *
 * @author Owen Butler
 */
public class Player
        extends BaseControllableDrawableGameObject
        implements MouseListener, PauseListener {

    private static final Logger logger = Logger.getLogger(Player.class.getName());

    private int facingX;

    private int facingY;

    private boolean firing;

    private float lastFireTimeStraight;
    private float lastFireTimeWide;
    private float lastMissileFireTime;

    private float fireTimeStraightInterval;
    private float fireTimeWideInterval;
    private float fireTimeMissileInterval;

    private int health = PlayerConstants.PLAYER_START_HEALTH;

    private int straightFirePowerup;
    private int wideFirePowerup;
    private int missilePowerup;

    public Player() {
        super(AssetConstants.gfx_playerShip, 0.0f, 0.0f, PlayerConstants.WIDTH, PlayerConstants.HEIGHT);

        setMoveVelX(PlayerConstants.SHIPVELOCITY);
        setMoveVelY(PlayerConstants.SHIPVELOCITY);

        setScreenClipRemove(false);

        setCollidable(true);
        setCollisionRadius(PlayerConstants.COLLISION_RADIUS);

        getSurface().enableAnimation(null, 4, 1);
        endFrame = 2;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

        // check periodically whether we need to spawn smoke cause we are damanged
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                checkSmoke();
            }
        });
        gameEngine.registerGameService("player", this);

        setClippedToScreen(true);

        init();
    }

    private void checkSmoke() {

        if (health < (PlayerConstants.PLAYER_START_HEALTH / 2 + 1)) {
            if (velX != 0 || velY != 0) {
                BackgroundCloud cloud = new BackgroundCloud(x, y);
                cloud.setWidth(32);
                cloud.setHeight(32);
                cloud.setRandomRotation();

                int scale = RandomUtils.nextInt(20) + 15;
                cloud.setScale(scale, scale);
                cloud.setFadeAndRemove(2);
                cloud.setVelXY(RandomUtils.nextInt(20) - 10, RandomUtils.nextInt(20) - 10);

                gameEngine.addGameObject(cloud);
            }
        }
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

        lookat(facingX, facingY);

        checkFire();
    }

    private void checkFire() {
        if (firing) {
            float time = gameEngine.getCurrentTime();

            if (lastFireTimeStraight < time) {
                lastFireTimeStraight = time + fireTimeStraightInterval;
                fire();
            }

            if (lastFireTimeWide < time) {
                lastFireTimeWide = time + fireTimeWideInterval;
                fireSmall();
            }

            if (lastMissileFireTime < time) {
                lastMissileFireTime = time + fireTimeMissileInterval;
                fireMissile();
            }
        }
    }

    /**
     * Fire!
     */
    private void fire() {
        float[] bulletVel = getVelocityFacing(facingX, facingY, PlayerConstants.PLAYER_BULLET_SPEED);

        bulletVel[0] += RandomUtils.nextInt(20) - 10;
        bulletVel[1] += RandomUtils.nextInt(20) - 10;

        PlayerBullet bullet = new PlayerBullet(this.x, this.y, bulletVel[0], bulletVel[1]);
        bullet.setRotation(surface.getRotation());
        bullet.setOwner(this);
        gameEngine.addGameObject(bullet);
    }

    private void fireSmall() {
        float[] bulletVel = getVelocityFacing(facingX, facingY, PlayerConstants.PLAYER_BULLET_SPEED_SMALL);

        bulletVel[0] += RandomUtils.nextInt(400) - 200;
        bulletVel[1] += RandomUtils.nextInt(200) - 100;

        SmallPlayerBullet smallBullet = new SmallPlayerBullet(this.x, this.y, bulletVel[0], bulletVel[1]);

        smallBullet.setRotation(surface.getRotation());
        smallBullet.setOwner(this);
        gameEngine.addGameObject(smallBullet);

    }

    private void fireMissile() {
        Missile missile = new Missile(this.x, this.y, facingX, facingY);
        missile.setOwner(this);
        gameEngine.addGameObject(missile);

    }

    public void mouseEvent(int x, int y) {
        facingX = x;
        facingY = y;
    }

    public void button0Down() {
        firing = true;

        if (health > 0) {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).loopSample();
        }
    }

    public void button0Up() {
        firing = false;
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).stopSample();
        if (health > 0) {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGunEnd).playSample();
        }
    }

    public void button1Down() {
    }

    public void button1Up() {
    }

    public void button2Down() {
    }

    public void button2Up() {
    }

    public int getFacingX() {
        return facingX;
    }

    public void setFacingX(int facingX) {
        this.facingX = facingX;
    }

    public int getFacingY() {
        return facingY;
    }

    public void setFacingY(int facingY) {
        this.facingY = facingY;
    }

    protected void damage(int amount) {
        health -= amount;

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerHit).playSample();

        if (health <= 0) {
            killed();
        }
    }

    private void killed() {

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).stopSample();

        DefaultGameFlowLogic gameFlow = (DefaultGameFlowLogic) gameEngine.getRegisteredGameService("gameFlowLogic");

        spawnDebri();
        spawnDebri();
        spawnDebri();

        for (int i = 0; i < 10; ++i) {

            float xPos = x + (RandomUtils.nextInt(60) - 30);
            float yPos = y + (RandomUtils.nextInt(60) - 30);
            TransExplosion transExplosion = new TransExplosion(xPos, yPos);
            transExplosion.setScale(20, 20);

            gameEngine.addGameObject(transExplosion);
        }

        gameFlow.playerDies();
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof EnemyBullet) {
            damage(1);
        }
    }

    public void init() {

        setXY(PlayerConstants.PLAYER_START_X, PlayerConstants.PLAYER_START_Y);
        facingX = PlayerConstants.PLAYER_START_X;

        health = PlayerConstants.PLAYER_START_HEALTH;

        straightFirePowerup = 1;
        wideFirePowerup = 1;
        missilePowerup = 1;

        fireTimeStraightInterval = PlayerConstants.FIRE_INTERVAL_BULLET;
        fireTimeWideInterval = PlayerConstants.FIRE_INTERVAL_WIDE;
        fireTimeMissileInterval = PlayerConstants.FIRE_INTERVAL_MISSILE;
    }

    protected void spawnDebri() {

        int numDebri = 10;
        for (int i = 0; i < numDebri; ++i) {
            SmallDebri smallDebri = new SmallDebri(x, y);
            smallDebri.setScale(-10, -10);
            gameEngine.addGameObject(smallDebri);

        }
    }

    /**
     * Pause requested.
     */
    public void pause() {

        // stop sounds
        if (firing) {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).stopSample();
        }

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_warAmbient).stopSample();
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerIdle).stopSample();

        gameEngine.pause();
    }

    /**
     * The opposite of pause.
     */
    public void unPause() {

        if (firing) {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).loopSample();
        }

        // start sounds that need to be started again
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_warAmbient).loopSample();
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerIdle).loopSample();

        gameEngine.unPause();
    }

    public void upgrade(Powerup powerup) {

        logger.trace("powerup");

        switch (powerup.getType()) {
            case Powerup.TYPE_MISSILE:
                missilePowerup++;
                fireTimeMissileInterval -= PlayerConstants.FIRE_POWERUP_MISSILE;
                break;
            case Powerup.TYPE_SHOT_STRAIGHT:
                straightFirePowerup++;
                fireTimeStraightInterval -= PlayerConstants.FIRE_POWERUP_BULLET;
                break;
            case Powerup.TYPE_SHOT_WIDE:
                wideFirePowerup++;
                fireTimeWideInterval -= PlayerConstants.FIRE_POWERUP_WIDE;
                break;
            default:
                break;
        }
    }
}

