package org.jgameengine.testgame.network.packets;

import java.util.List;
import java.io.Serializable;

/**
 * User: Owen Butler
 * Date: 19/04/2005
 * Time: 23:19:30
 */
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
