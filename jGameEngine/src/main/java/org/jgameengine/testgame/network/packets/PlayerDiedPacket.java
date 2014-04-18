package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class PlayerDiedPacket
        implements Serializable {

    private int id;

    private String name;


    public PlayerDiedPacket(int id, String name) {
        this.id = id;
        this.name = name;
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
}
