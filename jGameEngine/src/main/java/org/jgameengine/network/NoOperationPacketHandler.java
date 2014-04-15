package org.jgameengine.network;

import org.jgameengine.engine.Engine;

public class NoOperationPacketHandler implements NetworkPacketHandler {

    public void initPacketHandler(Engine engine) {
    }

    public void handlePacket(Integer connectionId, Object packet) {
    }

    public void handleDisconnection(Integer connectionId) {
    }
}
