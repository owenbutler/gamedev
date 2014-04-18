package org.owenbutler.theta.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.owenbutler.theta.constants.AssetConstants;
import org.owenbutler.theta.constants.GameConstants;

public abstract class LargeEnemySupport extends AbstractEnemyBase {

    protected int fireType;
    protected int numFireTypes = 4;

    protected float waypointX;
    protected float waypointY;

    protected boolean stopTrackingMovement;

    private float adjustedBulletSpeed = GameConstants.ENEMY_BULLET_SPEED;
    protected boolean fireIncreasesSpeed;

    protected float fireInterval = 1.0f;
    private int numBulletsFireType1 = 20;
    private float fireType1Time = 1.0f;

    private int numBulletsFireType2 = 9;
    private float fireType2Time = 1.0f;

    private int numBulletsFireType3 = 9;
    private float fireType3Time = 1.0f;

    private int numBulletsFireType4 = 8;
    private float fireType4Time = 1.0f;

    private float pVecX2, pVecY2;

    private float pVecX;
    private float pVecY;

    private float eStartX, eStartY;

    protected BossAura bossAura;

    protected void initFireTypes(float fireInterval,
                                 int bullets1, float time1,
                                 int bullets2, float time2,
                                 int bullets3, float time3,
                                 int bullets4, float time4) {

        this.fireInterval = fireInterval;

        numBulletsFireType1 = bullets1;
        fireType1Time = time1;

        numBulletsFireType2 = bullets2;
        fireType2Time = time2;

        numBulletsFireType3 = bullets3;
        fireType3Time = time3;

        numBulletsFireType4 = bullets4;
        fireType4Time = time4;

        startFiring();
    }

    public LargeEnemySupport(String gfx, float x, float y, int width, int height) {
        super(gfx, x, y, width, height);

        int rotationAmount = RandomUtils.nextInt(100) + 100;
        if (RandomUtils.nextBoolean()) {
            rotationAmount = -rotationAmount;
        }
        setRotate(rotationAmount);

        initEnemyMovement();
    }

    protected void initBossMode() {

        bossAura = new BossAura(x, y, this);
        gameEngine.addGameObject(bossAura);

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bossSpawn).stopSample();
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bossSpawn).playSample();

    }

    private void initEnemyMovement() {

        setMovementWaypoint();

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.ENEMY_MOVEMENT_TRACK_INTERVAL, GameConstants.ENEMY_MOVEMENT_TRACK_INTERVAL, new Event() {
            public void trigger() {
                enemyMovementTrack();
            }
        });
    }

    private void enemyMovementTrack() {
        if (stopTrackingMovement) {
            return;
        }

        setVelXY(this.getVelocityFacing(waypointX, waypointY, GameConstants.ENEMY_MAX_SPEED));

        if (nearWayPoint()) {
            setMovementWaypoint();
        }
    }

    private boolean nearWayPoint() {

        final int fuzzyness = 10;

        return (((x > waypointX - fuzzyness) && (x < waypointX + fuzzyness)) && ((y > waypointY - fuzzyness) && (y < waypointY + fuzzyness)));
    }

    private void setMovementWaypoint() {

        waypointX = RandomUtils.nextInt(600) + 20;
        waypointY = RandomUtils.nextInt(350) + 20;
    }

    public void startFiring() {
        gameEngine.getEventHandler().addEventInLoop(this, 0, fireInterval, new Event() {
            public void trigger() {
                startFireSequence();
            }
        });

    }

    public void think() {
        baseDrawableThink();
    }

    protected void showDamage(PlayerBullet bullet) {

        final int divideFactor = 5;
        final int numberDebri = 5;

        for (int i = 0; i < numberDebri; ++i) {
            Debri debri = new Debri(bullet.getX(), bullet.getY());

            float[] debriVel = new float[2];
            debriVel[0] = -(bullet.getVelX() / divideFactor);
            debriVel[1] = -(bullet.getVelY() / divideFactor);

            debri.setVelXYWithRandomMod(debriVel, 100);

            gameEngine.addGameObject(debri);
        }

        Strike strike = new Strike(bullet.getX(), bullet.getY());
        strike.setRotation(bullet.getSurface().getRotation());
        gameEngine.addGameObject(strike);

//        incrementSize();

        // play damage sound
//        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyDamage).stopSample();
        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyDamage).playSample();
    }

    protected void die() {
        dead = true;

        setVelXY(0, 0);
        stopTrackingMovement = true;

        gameEngine.getEventHandler().removeEventsForOwner(this);

        setCollidable(false);

        Strike strike = new Strike(x, y);
        strike.setRotation(RandomUtils.nextInt(360));
        strike.setScale(8, 1000);
        gameEngine.addGameObject(strike);

        ExplosionSmoke explosionSmoke = new ExplosionSmoke(x, y);
        if (bossAura != null) {
            explosionSmoke.setFadeAndRemove(0.73f);
        }

        gameEngine.addGameObject(explosionSmoke);

        spawnDeathDebri(200);

        gameEngine.getEventHandler().addEventIn(this, 0.1f, new Event() {
            public void trigger() {
                Strike strike = new Strike(x, y);
                strike.setRotation(RandomUtils.nextInt(360));
                gameEngine.addGameObject(strike);

                int scaleX = 8, scaleY = 500, debriRange = 400;

                if (bossAura != null) {
                    scaleX = 16;
                    scaleY = 1000;
                    debriRange = 800;
                    spawnDeathDebri(debriRange);
                    spawnDeathDebri(debriRange);
                    spawnDeathDebri(debriRange);
                    spawnDeathDebri(debriRange);
                }

                strike.setScale(scaleX, scaleY);
                spawnDeathDebri(debriRange);
            }
        });

        setShrinkAndRemove(0.2f);

        if (bossAura != null) {
            gameEngine.removeGameObject(bossAura);
        }

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyDie).playSample();
    }

    private void spawnDeathDebri(int range) {
        final int numberDebri = 10;

        for (int i = 0; i < numberDebri; ++i) {
            Debri debri = new Debri(x, y);

            debri.setVelXYRandom(range);

            gameEngine.addGameObject(debri);
        }
    }

    private void startFireSequence() {

        fireType = RandomUtils.nextInt(4);
        fireIncreasesSpeed = false;
        adjustedBulletSpeed = GameConstants.ENEMY_BULLET_SPEED;

        switch (fireType) {
            case 0:
                fireType1();
                break;
            case 1:
                fireType2();
                break;
            case 2:
                fireType3();
                break;
            case 3:
                fireType4();
                break;
            default:

        }
    }

    private void fireType1() {

        eStartX = x;
        eStartY = y;

        pVecX = x - player.getX();
        pVecY = y - player.getY();

        double rotation = -(Math.PI / 2.0f);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireType1Time / (float) numBulletsFireType1, numBulletsFireType1, new Event() {
            public void trigger() {
                doFireType1();
            }
        });
    }

    private void doFireType1() {

        double rotation = -((Math.PI / 2.0f) / (float) numBulletsFireType1);

        fireBullet(pVecX + eStartX, pVecY + eStartY);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));
    }

    private void fireType2() {

        eStartX = x;
        eStartY = y;

        pVecX = x - player.getX();
        pVecY = y - player.getY();

        double rotation = -(Math.PI / 1.0f);

        pVecX2 = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY2 = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        pVecX = (float) ((pVecX * Math.cos(-rotation)) - (pVecY * Math.sin(-rotation)));
        pVecY = (float) ((pVecX * Math.sin(-rotation)) + (pVecY * Math.cos(-rotation)));

        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireType2Time / (float) numBulletsFireType2, numBulletsFireType2, new Event() {
            public void trigger() {
                doFireType2();
            }
        });
    }

    private void doFireType2() {

        double rotation = ((Math.PI / 4.0f) / (float) numBulletsFireType2);

        fireBullet(pVecX + eStartX, pVecY + eStartY);
        fireBullet(pVecX2 + eStartX, pVecY2 + eStartY);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        pVecX2 = (float) ((pVecX2 * Math.cos(-rotation)) - (pVecY2 * Math.sin(-rotation)));
        pVecY2 = (float) ((pVecX2 * Math.sin(-rotation)) + (pVecY2 * Math.cos(-rotation)));

    }

    private void fireType3() {
        eStartX = x;
        eStartY = y;

        pVecX = x - player.getX();
        pVecY = y - player.getY();

        double rotation = -(Math.PI / 1.5f);

        pVecX2 = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY2 = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        pVecX = (float) ((pVecX * Math.cos(-rotation)) - (pVecY * Math.sin(-rotation)));
        pVecY = (float) ((pVecX * Math.sin(-rotation)) + (pVecY * Math.cos(-rotation)));

        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireType3Time / (float) numBulletsFireType3, numBulletsFireType3, new Event() {
            public void trigger() {
                doFireType3();
            }
        });
    }

    private void doFireType3() {
        double rotation = ((Math.PI / 3.0f) / (float) numBulletsFireType3);

        fireBullet(pVecX + eStartX, pVecY + eStartY);
        fireBullet(pVecX2 + eStartX, pVecY2 + eStartY);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        pVecX2 = (float) ((pVecX2 * Math.cos(-rotation)) - (pVecY2 * Math.sin(-rotation)));
        pVecY2 = (float) ((pVecX2 * Math.sin(-rotation)) + (pVecY2 * Math.cos(-rotation)));
    }

    private void fireType4() {

        fireIncreasesSpeed = true;

        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireType4Time / (float) numBulletsFireType4, numBulletsFireType4, new Event() {
            public void trigger() {
                doFireType4();
            }
        });

    }

    private void doFireType4() {

        fireBullet(player.getX(), player.getY());

    }

    private void fireBullet(float xAim, float yAim) {

        if (fireIncreasesSpeed) {
            adjustedBulletSpeed += GameConstants.ENEMY_BULLET_SPEED_SPEEDUP;
        } else {
            adjustedBulletSpeed = GameConstants.ENEMY_BULLET_SPEED;
        }

        EnemyBullet bullet = new EnemyBullet(this.x, this.y);
        bullet.setVelXY(bullet.getVelocityFacing(xAim, yAim, adjustedBulletSpeed));
        gameEngine.addGameObject(bullet);

//        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGun).playSample();
    }

    private void decrementSize() {

        setWidth(width - GameConstants.ENEMY_HIT_GROW_SIZE);
        setHeight(height - GameConstants.ENEMY_HIT_GROW_SIZE);

    }

}
