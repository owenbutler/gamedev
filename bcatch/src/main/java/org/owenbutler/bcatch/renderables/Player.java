package org.owenbutler.bcatch.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.input.MouseListener;
import org.jgameengine.input.PauseListener;
import org.jgameengine.renderables.Blood;
import org.jgameengine.renderables.Particle;
import org.owenbutler.bcatch.constants.AssetConstants;
import org.owenbutler.bcatch.constants.GameConstants;
import org.owenbutler.bcatch.logic.GameLogic;

public class Player extends BaseControllableDrawableGameObject
        implements MouseListener, PauseListener {

    protected BulletCatcher bulletCatcher;

    protected int mouseX, mouseY;

    protected int numBulletsInCurrentFire;
    protected int bulletScore;

    protected boolean dead;

    protected boolean stillFiring;

    protected boolean bulletCatcherGone = false;

    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setMoveVelX(GameConstants.PLAYER_VELOCITY);
        setMoveVelY(GameConstants.PLAYER_VELOCITY);

        setScreenClipRemove(false);
        setClippedToScreen(true);

        eightWayMoveAdjust = true;

        setCollidable(true);

    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        baseDrawableThink();
    }

    /**
     * Called when we finish catching bullets.
     *
     * @param numCaughtBullets number of bullets we caught
     */
    public void unleashBullets(int numCaughtBullets) {

        numBulletsInCurrentFire = numCaughtBullets;

        stillFiring = true;
        bulletScore = 1;

        gameEngine.getEventHandler().addEventIn(this, 0.0f, new Event() {
            public void trigger() {
                fireReboundBullet();
            }
        });

    }

    private void fireReboundBullet() {

        if (stillFiring) {

            if (numBulletsInCurrentFire > 0) {
                ReboundBullet reboundBullet = new ReboundBullet(x, y);

                reboundBullet.setVelXYWithRandomMod(getVelocityFacing(mouseX, mouseY, GameConstants.REBOUND_BULLET_Y_VEL), 80);
                reboundBullet.setScore(bulletScore);
                gameEngine.addGameObject(reboundBullet);

                --numBulletsInCurrentFire;
                ++bulletScore;

                gameEngine.getEventHandler().addEventIn(this, GameConstants.PLAYER_FIRE_INTERVAL, new Event() {
                    public void trigger() {
                        fireReboundBullet();
                    }
                });
            }
        }
    }

    private void catchBullets() {

        bulletCatcher = new BulletCatcher(getX(), getY(), this);
        gameEngine.addGameObject(bulletCatcher);

        bulletCatcherGone = false;
    }

    private void releaseBullets() {

        if (bulletCatcherGone) {
            return;
        }

        bulletCatcher.releaseBullets();

        gameEngine.removeGameObject(bulletCatcher);

        bulletCatcherGone = true;
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof Bullet || otherBody instanceof Enemy) {

            GameLogic gameLogic = (GameLogic) gameEngine.getRegisteredGameService("game");
            gameLogic.playerDies();

            spawnAmazingPlayerDiesExplosion();
        }
    }

    private void spawnAmazingPlayerDiesExplosion() {

        // particle explosion
        for (int i = 0; i < 60; ++i) {

            int angle = RandomUtils.nextInt(360);
            int speed = RandomUtils.nextInt(70) + 200;

            Particle particle = new Particle(x, y);

            particle.setVelX((float) Math.cos(Math.toRadians(angle)) * speed);
            particle.setVelY((float) Math.sin(Math.toRadians(angle)) * speed);

            particle.setFadeAndRemove(2, 1);

            gameEngine.addGameObject(particle);
        }

        // particle explosion
        for (int i = 0; i < 80; ++i) {

            int angle = RandomUtils.nextInt(360);
            int speed = RandomUtils.nextInt(70);

            Blood particle = new Blood(x, y);
            particle.setVelX((float) Math.cos(Math.toRadians(angle)) * speed);
            particle.setVelY((float) Math.sin(Math.toRadians(angle)) * speed);
            particle.setFadeAndRemove(2);
            gameEngine.addGameObject(particle);
        }

    }

    public void mouseEvent(int x, int y) {
        mouseX = x;
        mouseY = y;
    }

    public void button0Down() {
        if (dead) {
            return;
        }

        catchBullets();

        stillFiring = false;
        numBulletsInCurrentFire = 0;
    }

    public void button0Up() {

        if (dead) {
            return;
        }

        releaseBullets();

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

    /**
     * Pause requested.
     */
    public void pause() {
        gameEngine.pause();
    }

    /**
     * The opposite of pause.
     */
    public void unPause() {
        gameEngine.unPause();
    }
}
