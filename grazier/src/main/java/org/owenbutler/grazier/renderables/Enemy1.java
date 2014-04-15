package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;
import org.owenbutler.grazier.constants.GameConstants;

public class Enemy1 extends BaseDrawableGameObject {

    protected int health;

    private int fireType;
    private int numFireTypes = 4;

    protected Player player;

    /**
     * constructor.
     */
    public Enemy1() {
        super(AssetConstants.gfx_enemy, 0, 0, 128.0f, 128.0f);

        setCollidable(true);
        setCollisionRadius(GameConstants.ENEMY_COLLISION_RADIUS);

//        surface.enableAnimation(null, 2, 2);
//        surface.selectAnimationFrame(RandomUtils.nextInt(3));

        health = GameConstants.ENEMY_HEALTH;

//        setRotate(RandomUtils.nextInt(100) - 50);

        setRotation(RandomUtils.nextInt(360));

//        fireType = RandomUtils.nextInt(numFireTypes);
        fireType = 0;

        gameEngine.getEventHandler().addEventInLoop(this, 0, 0.5f, new Event() {
            //        gameEngine.getEventHandler().addEventInLoop(this, (RandomUtils.nextFloat() * 3) + 2, 0.5f, new Event() {
            public void trigger() {
                startFireSequence();
            }
        });

        player = (Player) gameEngine.getRegisteredGameService("player");

        setInitialStartPosition();

//        setVelY(GameConstants.ENEMY_VEL);

        setSortZ(AssetConstants.z_enemy);
    }

    private void setInitialStartPosition() {

        x = RandomUtils.nextInt(600) + 100;
//        y = 200;
        y = -80;
    }

    private void startFireSequence() {

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

    float pVecX;
    float pVecY;

    float eStartX, eStartY;

    private void fireType1() {

        eStartX = x;
        eStartY = y;

        pVecX = x - player.getX();
        pVecY = y - player.getY();

        double rotation = -(Math.PI / 2.0f);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        float fireTime = 1.0f;
        int numBullets = 20;
        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireTime / (float) numBullets, numBullets, new Event() {
            public void trigger() {
                doFireType1();
            }
        });
    }

    private void doFireType1() {

        double rotation = -((Math.PI / 2.0f) / (float) 20);

        fireBullet(pVecX + eStartX, pVecY + eStartY);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));
    }

    float pVecX2, pVecY2;

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

        float fireTime = 1.0f;
        int numBullets = 9;
        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireTime / (float) numBullets, numBullets, new Event() {
            public void trigger() {
                doFireType2();
            }
        });
    }

    private void doFireType2() {

        double rotation = ((Math.PI / 4.0f) / (float) 10);

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

        float fireTime = 1.0f;
        int numBullets = 9;
        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireTime / (float) numBullets, numBullets, new Event() {
            public void trigger() {
                doFireType3();
            }
        });
    }

    private void doFireType3() {
        double rotation = ((Math.PI / 3.0f) / (float) 10);

        fireBullet(pVecX + eStartX, pVecY + eStartY);
        fireBullet(pVecX2 + eStartX, pVecY2 + eStartY);

        pVecX = (float) ((pVecX * Math.cos(rotation)) - (pVecY * Math.sin(rotation)));
        pVecY = (float) ((pVecX * Math.sin(rotation)) + (pVecY * Math.cos(rotation)));

        pVecX2 = (float) ((pVecX2 * Math.cos(-rotation)) - (pVecY2 * Math.sin(-rotation)));
        pVecY2 = (float) ((pVecX2 * Math.sin(-rotation)) + (pVecY2 * Math.cos(-rotation)));
    }

    private void fireType4() {

        float fireTime = 1.0f;
        int numBullets = 8;
        gameEngine.getEventHandler().addEventInRepeat(this, 0.0f, fireTime / (float) numBullets, numBullets, new Event() {
            public void trigger() {
                doFireType4();
            }
        });

    }

    private void doFireType4() {

        fireBullet(player.getX(), player.getY());

    }

    private void fireBullet(float xAim, float yAim) {

        EnemyBullet bullet = new EnemyBullet(this.x, this.y);
        bullet.setVelXY(bullet.getVelocityFacing(xAim, yAim, GameConstants.ENEMY_BULLET_SPEED));
        gameEngine.addGameObject(bullet);
    }

    private void fireBullet() {

//        Bullet bullet = new Bullet(this.x, this.y);
//
//        Player player = (Player) gameEngine.getRegisteredGameService("player");
//
//        bullet.setVelXYWithRandomMod(bullet.getVelocityFacing(player.getX(), player.getY(), GameConstants.BULLET_YVEL), 40);
//        gameEngine.addGameObject(bullet);
//
//        gameEngine.getEventHandler().addEventIn(this, RandomUtils.nextFloat() + 2, new Event() {
//            public void trigger() {
//                fireBullet();
//            }
//        });
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

        removeIfOffScreen();
    }

    private void removeIfOffScreen() {
        if (y > 800) {
            removeSelf();
        }
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {

        if (otherBody instanceof PlayerBullet) {
            incrementSize();

            takeDamage();

            checkDeath();
        }

//        if (otherBody instanceof ReboundBullet) {
//
//            health--;
//
//            // particle explosion
//            for (int i = 0; i < 20; ++i) {
//                Particle particle = new Particle(x, y);
//                particle.setVelXYRandom(70);
//                gameEngine.addGameObject(particle);
//            }
//
//            if (health == 0) {
//
//                removeSelf();
//
//                GameLogic gameLogic = (GameLogic) gameEngine.getRegisteredGameService("game");
//                gameLogic.scoreHit(((ReboundBullet) otherBody).getScore() );
//            }
//        }
    }

    private void checkDeath() {
        if (health <= 0) {

            enemyDie();
        }
    }

    public void enemyDie() {

        setCollidable(false);
        setShrinkAndRemove(0.2f);

        Flower flower = new Flower(x, y);

        flower.setWidth(1);
        flower.setHeight(1);

        flower.setScale(10, 10);
        flower.setVelXY(getVelX(), getVelY());
        flower.setSortZ(AssetConstants.FLOOR_FLOWER);
        flower.setRotate(false);

        gameEngine.addGameObject(flower);

//        gameEngine.removeGameObject(this);
    }

    private void takeDamage() {
        --health;

        EnemyDebri debri = new EnemyDebri(x, y);
        debri.setVelXYRandom(100);
        debri.setShrinkAndRemove(5);
        gameEngine.addGameObject(debri);
    }

    private void incrementSize() {

        setWidth(width + GameConstants.ENEMY_HIT_GROW_SIZE);
        setHeight(height + GameConstants.ENEMY_HIT_GROW_SIZE);

        gameEngine.getEventHandler().addEventIn(this, 0.1f, new Event() {
            public void trigger() {
                decrementSize();
            }
        });
    }

    private void decrementSize() {

        setWidth(width - GameConstants.ENEMY_HIT_GROW_SIZE);
        setHeight(height - GameConstants.ENEMY_HIT_GROW_SIZE);

    }

}