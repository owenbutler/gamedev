package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

/**
 * User: Owen Butler
 * Date: 19/04/2005
 * Time: 23:21:59
 */
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
