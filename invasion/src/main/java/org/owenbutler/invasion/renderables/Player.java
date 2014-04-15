package org.owenbutler.invasion.renderables;

import org.jgameengine.common.ArrayUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.input.MouseListener;
import org.owenbutler.invasion.constants.AssetConstants;
import org.owenbutler.invasion.constants.GameConstants;
import org.owenbutler.invasion.logic.InvasionGameLogic;

public class Player
        extends BaseControllableDrawableGameObject
        implements MouseListener {

    private int facingX;

    private int facingY;

    boolean firing;

    protected boolean dead;

    /**
     * constructor.
     *
     * @param x x position
     * @param y y position
     */
    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setCollidable(true);
        setCollisionRadius(GameConstants.PLAYER_COLLISION_RADIUS);
        setScreenClipRemove(false);
        setClippedToScreen(true);

        setMoveVelX(GameConstants.SHIPVELOCITY);
        setMoveVelY(0.0f);

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.PLAYER_FIRE_RATE, GameConstants.PLAYER_FIRE_RATE, new Event() {
            public void trigger() {
                checkFire();
            }
        });

    }

    private void checkFire() {
        if (dead) {
            return;
        }

        if (firing) {
            float[] vel = new float[]{0, -GameConstants.PLAYER_BULLET_SPEED};

            ArrayUtils.modArrayRandom2f(vel, GameConstants.PLAYER_BULLET_VAR);

            PlayerBullet1 bullet = new PlayerBullet1(x, y - 10);
//            bullet.setVelY(-GameConstants.PLAYER_BULLET_SPEED);
            bullet.setVelXY(vel);
            gameEngine.addGameObject(bullet);
//            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_playerFire).playSample();

        }
    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        if (dead) {
            return;
        }

//        lookat(facingX, facingY);

        baseDrawableThink();
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
        if (otherBody instanceof Spaceman || otherBody instanceof Enemy) {
            // we die!

            InvasionGameLogic invasionGameLogic = (InvasionGameLogic) gameEngine.getRegisteredGameService("game");
            invasionGameLogic.playerDies();
        }
    }

    public void mouseEvent(int x, int y) {

        facingX = x;
        facingY = y;

    }

    public void damaged() {

//        PlanetesimalGameLogic planetesimalGameLogic = (PlanetesimalGameLogic) gameEngine.getRegisteredGameService("game");
//        planetesimalGameLogic.playerDies();
    }

    public void dead() {
        this.dead = true;
    }

    public void button0Down() {
        firing = true;
    }

    public void button0Up() {
        firing = false;
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
}