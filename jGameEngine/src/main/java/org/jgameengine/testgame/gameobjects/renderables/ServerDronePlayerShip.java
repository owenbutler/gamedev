package org.jgameengine.testgame.gameobjects.renderables;

//import org.apache.log4j.Logger;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class ServerDronePlayerShip
        extends BaseDrawableGameObject {

    private static final float SHIPVELOCITY = 200.0f;

    private int facingX;

    private int facingY;

    private Integer connectionOwner;

    private static final float COLLISION_RADIUS = 10.0f;
    private static final float SIZE = COLLISION_RADIUS * 2.0f;


    public ServerDronePlayerShip() {
        super("sys/boundingCircle.png", 0.0f, 0.0f, SIZE, SIZE);

        setXY(512, 384);

        setMoveVelX(SHIPVELOCITY);
        setMoveVelY(SHIPVELOCITY);

        setCollidable(true);
        setCollisionRadius(COLLISION_RADIUS);
    }


    public void think() {
    }


    public int getFacingX() {
        return facingX;
    }


    public void setFacingX(int facingX) {
        this.facingX = facingX;
    }


    public int getFacingY() {
        return facingY;
    }


    public void setFacingY(int facingY) {
        this.facingY = facingY;
    }


    public Integer getConnectionOwner() {
        return connectionOwner;
    }


    public void setConnectionOwner(Integer connectionOwner) {
        this.connectionOwner = connectionOwner;
    }
}
