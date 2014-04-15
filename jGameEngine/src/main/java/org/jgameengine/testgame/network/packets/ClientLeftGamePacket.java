package org.jgameengine.testgame.network.packets;

import java.io.Serializable;

/**
 * packet sent to the clients by the server when a client either leaves the game, normally or because of connection troubles.
 *
 * User: Owen Butler
 * Date: 19/04/2005
 * Time: 23:12:22
 */
public class ClientLeftGamePacket
        implements Serializable {

    private int id;

    public ClientLeftGamePacket(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
