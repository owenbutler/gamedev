package org.jgameengine.network;

import org.apache.log4j.Logger;
import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;
import org.jgameengine.network.objectstream.ObjectStreamNetworkDriver;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ServerConnectionHandler
        implements ConnectionHandler {

    private static final Logger logger = Logger.getLogger(ServerConnectionHandler.class);

    private Engine engine;

    private String address;

    private int port;

    private ConnectionListener connectionListener;

    private List<Socket> newClientQueue;

    private Map<Integer, NetworkDriver> networkDrivers = new HashMap<>();

    private int nextConnectionId;

    public void setServerAddress(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void initNetwork(Engine engine)
            throws InitialisationException {
        this.engine = engine;

        newClientQueue = Collections.synchronizedList(new LinkedList<Socket>());
        connectionListener = new ConnectionListener(newClientQueue, port);

        connectionListener.init();
        connectionListener.start();
    }

    public void shutdownNetwork() {
        logger.debug("server shutdownNetwork");

        logger.debug("stopping connection listener");
        connectionListener.setRunning(false);
        connectionListener.interrupt();
        try {
            connectionListener.join();
            logger.debug("connection listener stopped");
        } catch (InterruptedException e) {
            logger.error("interrupted while waiting for connection manager to quit", e);
        }

        logger.debug("closing all connections");
        for (NetworkDriver connection : networkDrivers.values()) {
            connection.close();
        }
    }

    public void processNetwork() {
        while (newClientQueue.size() > 0) {
            Socket connectionSocket = newClientQueue.remove(0);

            NetworkDriver connectionDriver = new ObjectStreamNetworkDriver();

            try {
                connectionDriver.initialise(connectionSocket);

                networkDrivers.put(getNextConnectionId(), connectionDriver);
            } catch (InitialisationException e) {
                logger.error("error while creating connection", e);
            }
        }

        processConnections();
    }

    public void sendPacket(Integer connectionId, Object packet) {
        NetworkDriver connection = networkDrivers.get(connectionId);

        if (connection != null) {
            sendPacket(connection, packet);
        }
    }

    private void sendPacket(NetworkDriver connection, Object packet) {
        try {
            connection.writeObject(packet);
        } catch (NetworkException e) {
            logger.error("error writing packet", e);
        }
    }

    public void sendPacketToAll(Object packet) {
        for (NetworkDriver connection : networkDrivers.values()) {
            sendPacket(connection, packet);
        }
    }

    private void processConnections() {
        for (Iterator iterator = networkDrivers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer connectionId = (Integer) entry.getKey();
            NetworkDriver connection = (NetworkDriver) entry.getValue();

            if (!connection.isValid()) {
                logger.debug("removing bung connection");
                engine.getPacketHandler().handleDisconnection(connectionId);
                connection.close();
                iterator.remove();
            }

            while (connection.objectAvailable()) {
                Object gameObject = connection.getNext();
                engine.getPacketHandler().handlePacket(connectionId, gameObject);
            }
        }
    }

    private Integer getNextConnectionId() {
        Integer connectionId = nextConnectionId;
        nextConnectionId++;
        return connectionId;
    }

}
