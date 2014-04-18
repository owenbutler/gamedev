package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class RemoveMissilePacket
        implements Serializable {

    private int id;


    public RemoveMissilePacket(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
}
