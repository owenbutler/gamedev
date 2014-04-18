package org.owenbutler.JGE_ARTIFACT_ID.renderables;

import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.owenbutler.JGE_ARTIFACT_ID.constants.AssetConstants;
import org.owenbutler.JGE_ARTIFACT_ID.constants.GameConstants;
import org.owenbutler.JGE_ARTIFACT_ID.logic.GameLogic;

public class Player extends BaseControllableDrawableGameObject implements MouseListener {

    protected boolean dead;

    protected boolean firing;

    protected int facingX;

    protected int facingY;

    protected GameLogic game;

    public Player(float x, float y) {
        super(AssetConstants.gfx_player, x, y, GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        setMoveVelX(GameConstants.PLAYER_VELOCITY);
        setMoveVelY(GameConstants.PLAYER_VELOCITY);

        game = (GameLogic) gameEngine.getRegisteredGameService("game");
    }

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
