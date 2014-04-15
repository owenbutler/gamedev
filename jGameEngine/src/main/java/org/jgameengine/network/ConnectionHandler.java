package org.jgameengine.network;

import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;

public interface ConnectionHandler {

    void setServerAddress(String address, int port);

    void initNetwork(Engine engine) throws InitialisationException;

    void shutdownNetwork();

    void processNetwork();

    void sendPacket(Integer connectionId, Object packet);

    void sendPacketToAll(Object packet);

}
