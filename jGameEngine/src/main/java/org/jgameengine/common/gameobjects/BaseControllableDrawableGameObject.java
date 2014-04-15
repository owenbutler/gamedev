package org.jgameengine.common.gameobjects;

import org.jgameengine.input.MovementListener;

public abstract class BaseControllableDrawableGameObject
        extends BaseDrawableGameObject implements MovementListener {

    protected boolean eightWayMoveAdjust;

    public BaseControllableDrawableGameObject(String surfaceName, float x, float y, float width, float height) {
        super(surfaceName, x, y, width, height);
    }

    public abstract void think();

    public void move() {

        float tVelX = 0, tVelY = 0;
        tVelX = velX;
        tVelY = velY;

        if (eightWayMoveAdjust) {
            if (Math.abs(velX) == Math.abs(velY)) {
                tVelX = velX * 0.80f;
                tVelY = velY * 0.80f;
            }
        }

        x += gameEngine.getTimeDelta() * tVelX;
        y += gameEngine.getTimeDelta() * tVelY;

        if (clippedToScreen) {
            clipToScreen();
        }
        surface.setXY(x, y);
    }

    public void inputLeft(int magnitude) {
        velX += (magnitude > 0) ? -moveVelX : moveVelX;
    }

    public void inputRight(int magnitude) {
        velX += (magnitude > 0) ? moveVelX : -moveVelX;
    }

    public void inputUp(int magnitude) {
        velY += (magnitude > 0) ? -moveVelY : moveVelY;
    }

    public void inputDown(int magnitude) {
        velY += (magnitude > 0) ? moveVelY : -moveVelY;
    }
}
