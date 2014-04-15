package org.jgameengine.network;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;
import org.jgameengine.network.objectstream.ObjectStreamNetworkDriver;
import org.jgameengine.testgame.network.packets.JoinPacket;

import java.io.IOException;
import java.net.Socket;

public class ClientConnectionHandler implements ConnectionHandler {

    private static final Logger logger = Logger.getLogger(ClientConnectionHandler.class.getName());

    private String serverAddress;

    private int serverPort;

    private ObjectStreamNetworkDriver networkDriver;

    private Engine engine;

    public void setServerAddress(String address, int port) {
        serverAddress = address;
        serverPort = port;
    }

    public void initNetwork(Engine engine) throws InitialisationException {
        networkDriver = new ObjectStreamNetworkDriver();

        try {
            logger.debug("connecting to " + serverAddress + ":" + serverPort);
            networkDriver.initialise(new Socket(serverAddress, serverPort));
            logger.debug("connected");

            logger.debug("joining");
            sendPacketToAll(new JoinPacket("noname"));
        } catch (IOException e) {
            throw new InitialisationException("error connecting to server", e);
        }
    }

    public void shutdownNetwork() {
        logger.debug("client shutdownNetwork");

        try {
            networkDriver.close();
        } catch (Exception e) {
            logger.warn("exception while closing connection", e);
        }
    }

    public void processNetwork() {
        while (networkDriver.objectAvailable()) {
            Object gameObject = networkDriver.getNext();
            if (logger.isTraceEnabled()) {
                logger.trace("got a packet : " + ToStringBuilder.reflectionToString(gameObject));
            }

            engine.getPacketHandler().handlePacket(null, gameObject);
        }
    }

    public void sendPacket(Integer connectionId, Object packet) {
        networkDriver.writeObject(packet);
    }

    public void sendPacketToAll(Object packet) {
        networkDriver.writeObject(packet);
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
