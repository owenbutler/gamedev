package org.jgameengine.testgame.network.packets;

import java.util.List;
import java.io.Serializable;

/**
 * User: Owen Butler
 * Date: 19/04/2005
 * Time: 23:07:44
 */
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
