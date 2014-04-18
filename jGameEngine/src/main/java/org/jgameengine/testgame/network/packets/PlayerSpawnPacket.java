package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class PlayerSpawnPacket
        implements Serializable {

    private int id;

    private String name;

    private float positionX;

    private float positionY;

    private float directionX;

    private float directionY;


    public PlayerSpawnPacket(int id, String name, float positionX, float positionY, float directionX, float directionY) {
        this.id = id;
        this.name = name;
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


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
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
}
