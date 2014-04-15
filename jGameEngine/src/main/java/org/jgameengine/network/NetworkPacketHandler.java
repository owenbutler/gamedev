package org.jgameengine.network;

import org.jgameengine.engine.Engine;

public interface NetworkPacketHandler {

    void initPacketHandler(Engine engine);

    void handlePacket(Integer connectionId, Object packet);

    void handleDisconnection(Integer connectionId);

}
