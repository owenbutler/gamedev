package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class MissilePacket
        implements Serializable {

    private int id;

    private float positionX;

    private float positionY;

    private float directionX;

    private float directionY;

    private float velocityX;

    private float velocityY;


    public MissilePacket(int id, float positionX, float positionY, float directionX, float directionY, float velocityX, float velocityY) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.directionX = directionX;
        this.directionY = directionY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
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


    public float getDirectionX() {
        return directionX;
    }


    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }


    public float getDirectionY() {
        return directionY;
    }


    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }


    public float getVelocityX() {
        return velocityX;
    }


    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }


    public float getVelocityY() {
        return velocityY;
    }


    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}
