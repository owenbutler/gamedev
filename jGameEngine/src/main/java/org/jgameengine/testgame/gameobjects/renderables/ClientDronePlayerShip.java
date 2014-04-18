package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class ClientDronePlayerShip
        extends BaseDrawableGameObject {

//    private static final Logger logger = Logger.getLogger(ServerDronePlayerShip.class.getName());

    private static final float SHIPVELOCITY = 200.0f;

    private int facingX;

    private int facingY;

    private Integer connectionOwner;

    private static final float WIDTH = 80.0f;
    private static final float HEIGHT = 80.0f;


    public ClientDronePlayerShip() {
        super("ship1.png", 0.0f, 0.0f, WIDTH, HEIGHT);

        setXY(512, 384);

        setMoveVelX(SHIPVELOCITY);
        setMoveVelY(SHIPVELOCITY);

        setCollidable(true);
        setCollisionRadius(10.0f);
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
