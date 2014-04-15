package org.jgameengine.network;

import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;

public class NoOperationConnectionHandler implements ConnectionHandler {

    public void setServerAddress(String address, int port) {
    }

    public void initNetwork(Engine engine) throws InitialisationException {
    }

    public void shutdownNetwork() {
    }

    public void processNetwork() {
    }

    public void sendPacket(Integer connectionId, Object packet) {
    }

    public void sendPacketToAll(Object packet) {
    }
}
