package org.owenbutler.theta.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.input.MouseListener;
import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.constants.GameConstants;
import org.owenbutler.theta.logic.GameLogic;
import org.owenbutler.theta.logic.HudManager;

/**
 * Player ship for the theta game.
 *
 * @author Owen Butler
 */
public class Player extends BaseControllableDrawableGameObject implements MouseListener {

    private int facingX;

    private int facingY;

    private boolean firing;

    protected boolean dead;

    protected int health;

    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setMoveVelX(GameConstants.PLAYER_VELOCITY);
        setMoveVelY(GameConstants.PLAYER_VELOCITY);

        eightWayMoveAdjust = true;

        setClippedToScreen(true);

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.PLAYER_FIRE_INTERVAL, GameConstants.PLAYER_FIRE_INTERVAL, new Event() {
            public void trigger() {
                checkPlayerFire();
            }
        });

        setSortZ(AssetConstants.Z_PLAYER);

        facingX = GameConstants.PLAYER_SPAWN_X;

        setCollidable(true);
        setCollisionRadius(3);

        health = GameConstants.PLAYER_HEALTH;
    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        baseDrawableThink();

        lookat(facingX, facingY);
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {

        if (otherBody instanceof EnemyBullet || otherBody instanceof SmallHomingEnemy) {

            damaged((BaseDrawableGameObject) otherBody);
        } else if (otherBody instanceof LargeEnemySupport) {

            destroyed((BaseDrawableGameObject) otherBody);
        }
    }

    private void destroyed(BaseDrawableGameObject hitMe) {

        GameLogic game = (GameLogic) gameEngine.getRegisteredGameService("game");

        setCollidable(false);
        game.playerDies();

        gameEngine.getEventHandler().addEventInRepeat(this, 0.05f, 0.05f, 20, new Event() {
            public void trigger() {

                spawnStrike();

            }
        });

        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, 0.1f, 10, new Event() {
            public void trigger() {
                ExplosionSmoke explosionSmoke = new ExplosionSmoke(x, y);
                gameEngine.addGameObject(explosionSmoke);

                final int numberDebri = 15;

                for (int i = 0; i < numberDebri; ++i) {
                    Debri debri = new Debri(x, y);

                    debri.setVelXYRandom(300);

                    gameEngine.addGameObject(debri);
                }

            }
        });

        dead = true;
    }

    private void spawnStrike() {

        Strike strike = new Strike(x, y);
        strike.setRotation(RandomUtils.nextInt(360));
        strike.setScale(8, RandomUtils.nextInt(200) + 400);
        gameEngine.addGameObject(strike);

    }

    private void damaged(BaseDrawableGameObject hitMe) {
        --health;

        updateHud();

        // show some damage
        final int numberDebri = 10;

        for (int i = 0; i < numberDebri; ++i) {
            Debri debri = new Debri(x, y);

            float[] vel = new float[2];
            vel[0] = hitMe.getVelX();
            vel[1] = hitMe.getVelY();

            debri.setVelXYWithRandomMod(vel, 100);

            gameEngine.addGameObject(debri);
        }

        if (health == 0) {
            destroyed(hitMe);
        }

        // play damaged sound effect
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerDamage).playSample();

        setCollidable(false);

        gameEngine.getEventHandler().addEventIn(this, 2.0f, new Event() {
            public void trigger() {
                setCollidable(true);
                getSurface().setAlpha(1.0f);
            }
        });

        gameEngine.getEventHandler().addEventInRepeat(this, 0.02f, 0.02f, 90, new Event() {
            public void trigger() {

                buggerWithAlpha();
            }
        });

        // spawn a player hit wave

        gameEngine.addGameObject(new PlayerHitWave(x, y));
    }

    private void buggerWithAlpha() {

        getSurface().setAlpha((float) ((Math.sin(gameEngine.getCurrentTime() * 20) + 1.0f) / 2.0f));
    }

    private void updateHud() {
        HudManager hudManager = (HudManager) gameEngine.getHudManager();
        hudManager.setLifeText(Integer.toString(health));
    }

    private void checkPlayerFire() {

        if (dead) {
            return;
        }

        if (firing) {
            fire();
        }
    }

    private void fire() {
        float[] bulletVel = getVelocityFacing(facingX, facingY, GameConstants.PLAYER_BULLET_SPEED);

//        bulletVel[0] += RandomUtils.nextInt(20) - 10;
//        bulletVel[1] += RandomUtils.nextInt(20) - 10;

        PlayerBullet bullet = new PlayerBullet(this.x, this.y);
        bullet.setRotation(surface.getRotation());
        bullet.setOwner(this);
        bullet.setVelXYWithRandomMod(bulletVel, 50);
        gameEngine.addGameObject(bullet);
    }

    public void mouseEvent(int x, int y) {
        facingX = x;
        facingY = y;
    }

    public void button0Down() {

        firing = true;

        // play a sound effect?
        if (!dead) {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).loopSample();
        }

    }

    public void button0Up() {

        firing = false;

        // stop the playing sound effect?
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerGun).stopSample();
        if (!dead) {
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

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void init() {
        health = GameConstants.PLAYER_HEALTH;
        setCollidable(true);
        updateHud();
    }
}
