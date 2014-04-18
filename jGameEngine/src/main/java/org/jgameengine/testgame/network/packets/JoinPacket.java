package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

public class JoinPacket
        implements Serializable {

    private String name;


    public JoinPacket(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
