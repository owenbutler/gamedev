package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class PlayerDamagePacket
        implements Serializable {

    private int id;

    private float damage;


    public PlayerDamagePacket(int id, float damage) {
        this.id = id;
        this.damage = damage;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public float getDamage() {
        return damage;
    }


    public void setDamage(float damage) {
        this.damage = damage;
    }
}
