package org.jgameengine.testgame.network.packets;

import java.io.Serializable;
import java.util.List;

public class WorldStatePacket
        implements Serializable {

    private List worldState;


    public WorldStatePacket(List worldState) {
        this.worldState = worldState;
    }


    public List getWorldState() {
        return worldState;
    }


    public void setWorldState(List worldState) {
        this.worldState = worldState;
    }
}
