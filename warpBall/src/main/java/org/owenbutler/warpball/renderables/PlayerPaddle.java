package org.owenbutler.warpball.renderables;

import org.jgameengine.common.gameobjects.BaseControllableDrawableGameObject;
import org.jgameengine.input.MouseListener;
import org.owenbutler.warpball.constants.AssetConstants;
import org.owenbutler.warpball.logic.WarpBallLogic;

public class PlayerPaddle
        extends BaseControllableDrawableGameObject
        implements MouseListener {

    protected int mouseX;
    protected int mouseY;

    public PlayerPaddle(float x, float y) {
        super(AssetConstants.gfx_paddle, x, y, 32, 128);
    }


    public void think() {

        setY(clipMouseY());

        baseDrawableThink();
    }


    final int minY = 60;
    final int maxY = 600 - minY;


    private int clipMouseY() {

        if (mouseY < minY) {
            mouseY = minY;
        } else if (mouseY > maxY) {
            mouseY = maxY;
        }

        return mouseY;
    }


    public void mouseEvent(int x, int y) {
        mouseX = x;
        mouseY = y;
    }


    public void button0Down() {

    }


    public void button0Up() {
        WarpBallLogic warpBallLogic = (WarpBallLogic) gameEngine.getRegisteredGameService("game");

        warpBallLogic.playerPressedButton();
    }


    public void button1Down() {

    }


    public void button1Up() {

    }


    public void button2Down() {

    }


    public void button2Up() {

    }
}