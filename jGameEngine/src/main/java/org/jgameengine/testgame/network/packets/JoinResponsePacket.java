package org.jgameengine.testgame.network.packets;

import java.io.Serializable;
import java.util.List;

public class JoinResponsePacket
        implements Serializable {

    private List objects;

    private int connectionId;


    public JoinResponsePacket(List objects, int connectionId) {
        this.objects = objects;
        this.connectionId = connectionId;
    }


    public List getObjects() {
        return objects;
    }


    public void setObjects(List objects) {
        this.objects = objects;
    }


    public int getConnectionId() {
        return connectionId;
    }


    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
