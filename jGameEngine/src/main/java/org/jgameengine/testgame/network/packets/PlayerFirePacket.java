package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class PlayerFirePacket
        implements Serializable {

    private int id;

    private float positionX;

    private float positionY;

    private int directionX;

    private int directionY;


    public PlayerFirePacket(int id, float positionX, float positionY, int directionX, int directionY) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.directionX = directionX;
        this.directionY = directionY;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public float getPositionX() {
        return positionX;
    }


    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }


    public float getPositionY() {
        return positionY;
    }


    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }


    public int getDirectionX() {
        return directionX;
    }


    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }


    public int getDirectionY() {
        return directionY;
    }


    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }
}
