package org.owenbutler.goose.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.input.MouseListener;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.constants.GameConstants;
import org.owenbutler.goose.logic.GameLogic;

public class Player extends BaseControllableDrawableGameObject implements MouseListener {

    protected boolean dead;

    protected boolean firing;

    protected int facingX;

    protected int facingY;

    protected GameLogic game;

    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setCollidable(true);
        setCollisionRadius(8);

        setScreenClipRemove(false);
        setClippedToScreen(true);

        eightWayMoveAdjust = true;

        setMoveVelX(GameConstants.PLAYER_VELOCITY);
        setMoveVelY(GameConstants.PLAYER_VELOCITY);

        game = (GameLogic) gameEngine.getRegisteredGameService("game");

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.PLAYER_FIRE_INTERVAL, GameConstants.PLAYER_FIRE_INTERVAL, new Event() {
            public void trigger() {
                checkPlayerFire();
            }
        });

        setSortZ(AssetConstants.Z_PLAYER);
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

        float bulletX = x + (RandomUtils.nextInt(20) - 10);

        PlayerBullet bullet = new PlayerBullet(bulletX, y - 10);
        gameEngine.addGameObject(bullet);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        baseDrawableThink();
    }

    public void mouseEvent(int x, int y) {
        facingX = x;
        facingY = y;
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
