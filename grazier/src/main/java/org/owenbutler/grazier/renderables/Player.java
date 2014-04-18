package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.input.MouseListener;
import org.owenbutler.grazier.constants.AssetConstants;
import org.owenbutler.grazier.constants.GameConstants;
import org.owenbutler.grazier.logic.GameLogic;

import java.util.Set;

public class Player extends BaseControllableDrawableGameObject implements MouseListener {

    protected GameLogic game;

    protected boolean dead;

    protected boolean firing;
    protected boolean firing2;

    protected int numGrazed = 0;
    private SmallRainbow smallRainbow;

    private int numFrames;

    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setMoveVelX(GameConstants.PLAYER_VELOCITY);
        setMoveVelY(GameConstants.PLAYER_VELOCITY);

        setClippedToScreen(true);

        eightWayMoveAdjust = true;

        setCollidable(true);

        game = (GameLogic) gameEngine.getRegisteredGameService("game");

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.PLAYER_FIRE_INTERVAL, GameConstants.PLAYER_FIRE_INTERVAL, new Event() {
            public void trigger() {
                checkPlayerFire();
            }
        });

        setSortZ(AssetConstants.z_player);
    }

    public void grazedABullet() {
        ++numGrazed;

        if (numGrazed == 10) {
            smallRainbow = new SmallRainbow(780, 580);
            gameEngine.addGameObject(smallRainbow);
        }
        if (numGrazed >= 10) {
            numGrazed = 10;
        }
    }

    private void checkPlayerFire() {
        if (dead) {
            return;
        }

        if (firing) {

            doFire();
        }

        if (firing2) {
            doFire2();
        }
    }

    private void doFire2() {

        if (numGrazed == 10) {

            // flash the screen
            spawnRainbow();

            // turn all bullets into flowers
            // turn enemies into flower explosions.
            Set<GameObject> gameObjects = gameEngine.getGameObjects();
            for (GameObject gameObject : gameObjects) {

                boolean spawnFlower = false;
                float fX = 0, fY = 0;

                if (gameObject instanceof EnemyBullet) {

                    EnemyBullet bullet = (EnemyBullet) gameObject;

                    spawnFlower = true;
                    fX = bullet.getX();
                    fY = bullet.getY();

                } else if (gameObject instanceof EnemyBulletGrazed) {

                    EnemyBulletGrazed bullet = (EnemyBulletGrazed) gameObject;

                    spawnFlower = true;
                    fX = bullet.getX();
                    fY = bullet.getY();
                }

                if (spawnFlower) {
                    Flower flower = new Flower(fX, fY);
                    flower.setShrinkAndRemove(20);
                    flower.setWidth(32);
                    flower.setHeight(32);

                    flower.setVelY(GameConstants.ENEMY_VEL);

                    gameEngine.addGameObject(flower);
                    gameEngine.removeGameObject(gameObject);
                }
            }

            numGrazed = 0;

            Rainbow rainbow = new Rainbow(0, 0);
            gameEngine.addGameObject(rainbow);

            gameEngine.removeGameObject(smallRainbow);
        }

    }

    private void spawnRainbow() {

    }

    private void doFire() {

        float bulletx = (x + (RandomUtils.nextInt(40) - 20));

        PlayerBullet playerBullet = new PlayerBullet(bulletx, y - 10);
        gameEngine.addGameObject(playerBullet);
    }

    public void think() {

        ++numFrames;
        if (numFrames == 4000) {
            gameEngine.shutdownEngine();
        }

        baseDrawableThink();
    }

    public void collision(Collidable otherBody) {

//        if (dead) {
//            return;
//        }
//
//        if (otherBody instanceof Enemy1 || otherBody instanceof EnemyBulletGrazed) {
//
//            explode();
//
//            game.playerDies();
//
//            dead = true;
//        }
    }

    private void explode() {

    }

    public void mouseEvent(int x, int y) {

    }

    public void button0Down() {

        firing = true;
    }

    public void button0Up() {

        firing = false;
    }

    public void button1Down() {
        firing2 = true;

    }

    public void button1Up() {
        firing2 = false;

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

}
