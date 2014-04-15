package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

/**
 * User: Owen Butler
 * Date: 19/04/2005
 * Time: 23:06:31
 */
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
