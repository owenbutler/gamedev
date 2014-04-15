package org.owenbutler.bcatch.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.renderables.Particle;
import org.owenbutler.bcatch.constants.AssetConstants;
import org.owenbutler.bcatch.constants.GameConstants;
import org.owenbutler.bcatch.logic.GameLogic;
import org.owenbutler.bcatch.logic.WayPoint;

public class Enemy extends BaseDrawableGameObject {

    protected int health;

    private int fireType;
    private int numFireTypes = 4;

    protected Player player;

    /**
     * constructor.
     */
    public Enemy() {
        super(AssetConstants.gfx_enemy, 0, 0, 16.0f, 16.0f);

        setCollidable(true);
        setCollisionRadius(GameConstants.ENEMY_COLLISION_RADIUS);
        setScreenClipRemove(false);

//        surface.enableAnimation(null, 2, 2);
//        surface.selectAnimationFrame(RandomUtils.nextInt(3));

        health = GameConstants.ENEMY_HEALTH;

        setRotate(RandomUtils.nextInt(500) - 250);

        fireType = RandomUtils.nextInt(numFireTypes);
//        fireType = 3;

        gameEngine.getEventHandler().addEventIn(this, RandomUtils.nextFloat() + 2, new Event() {
            public void trigger() {
                startFireSequence();
            }
        });

        player = (Player) gameEngine.getRegisteredGameService("player");
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

        Bullet bullet = new Bullet(this.x, this.y);

        bullet.setVelXY(bullet.getVelocityFacing(xAim, yAim, GameConstants.BULLET_YVEL));
        gameEngine.addGameObject(bullet);
    }

    private void fireBullet() {

        Bullet bullet = new Bullet(this.x, this.y);

        Player player = (Player) gameEngine.getRegisteredGameService("player");

        bullet.setVelXYWithRandomMod(bullet.getVelocityFacing(player.getX(), player.getY(), GameConstants.BULLET_YVEL), 40);
        gameEngine.addGameObject(bullet);

        gameEngine.getEventHandler().addEventIn(this, RandomUtils.nextFloat() + 2, new Event() {
            public void trigger() {
                fireBullet();
            }
        });
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {

        if (otherBody instanceof ReboundBullet) {

            health--;

            // particle explosion
            for (int i = 0; i < 20; ++i) {
                Particle particle = new Particle(x, y);
                particle.setVelXYRandom(70);
                gameEngine.addGameObject(particle);
            }

            if (health == 0) {

                removeSelf();

                GameLogic gameLogic = (GameLogic) gameEngine.getRegisteredGameService("game");
                gameLogic.scoreHit(((ReboundBullet) otherBody).getScore());
            }
        }
    }

    protected WayPoint[] myRoute;
    protected int nextPosition = 0;

    public void setRoute(WayPoint[] currentRoute) {

        myRoute = currentRoute;

        setPositionFromRoute();

        startTrackingRoute();

    }

    private void startTrackingRoute() {

        setVelXY(getVelocityFacing(myRoute[0].x, myRoute[0].y, GameConstants.ENEMY_SPEED));

        float diffX = Math.abs(x - myRoute[0].x);
        float diffY = Math.abs(y - myRoute[0].y);

        float lengthToPoint = (float) Math.sqrt(diffX * diffX + diffY * diffY);

        // set an event when your about at the next waypoint
        gameEngine.getEventHandler().addEventIn(this, lengthToPoint * (1.0f / GameConstants.ENEMY_SPEED), new Event() {
            public void trigger() {
                setNextWayPoint();
            }
        });

    }

    private void setNextWayPoint() {

        nextPosition++;

        if (nextPosition == myRoute.length) {
            gameEngine.getEventHandler().addEventIn(this, 0.5f, new Event() {
                public void trigger() {
                    removeSelf();
                }
            });

            return;
        }

        setVelXY(getVelocityFacing(myRoute[nextPosition].x, myRoute[nextPosition].y, GameConstants.ENEMY_SPEED));

        float diffX = Math.abs(x - myRoute[nextPosition].x);
        float diffY = Math.abs(y - myRoute[nextPosition].y);

        float lengthToPoint = (float) Math.sqrt(diffX * diffX + diffY * diffY);

        // set an event when your about at the next waypoint
        gameEngine.getEventHandler().addEventIn(this, lengthToPoint * (1.0f / GameConstants.ENEMY_SPEED), new Event() {
            public void trigger() {
                setNextWayPoint();
            }
        });

    }

    private void setPositionFromRoute() {

        // find the edge of the screen we are closest to
        int lengthFromTop, lengthFromBottom, lengthFromLeft, lengthFromRight;

        lengthFromTop = myRoute[0].y;
        lengthFromBottom = 600 - myRoute[0].y;
        lengthFromLeft = myRoute[0].x;
        lengthFromRight = 800 - myRoute[0].x;

        // are we closest to left edge?
        if (lengthFromLeft < lengthFromRight && lengthFromLeft < lengthFromTop && lengthFromLeft < lengthFromBottom) {

            x = -20;
            y = myRoute[0].y;

        } else if (lengthFromRight < lengthFromLeft && lengthFromRight < lengthFromTop && lengthFromRight < lengthFromBottom) {

            x = 820;
            y = myRoute[0].y;

        } else if (lengthFromTop < lengthFromBottom && lengthFromTop < lengthFromRight && lengthFromTop < lengthFromLeft) {

            x = myRoute[0].x;
            y = -20;

        } else if (lengthFromBottom < lengthFromTop && lengthFromBottom < lengthFromRight && lengthFromBottom < lengthFromLeft) {

            x = myRoute[0].x;
            y = 620;

        }
    }
}