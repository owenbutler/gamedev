package org.jgameengine.network;

import org.jgameengine.common.InitialisationException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;

public class ConnectionListener
        extends Thread {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConnectionListener.class);

    private int portNumber;

    private List newClientQueue;

    private ServerSocket serverSocket;

    private boolean running = true;

    private static final int LISTEN_TIMEOUT = 1000;

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public ConnectionListener(List newClientQueue, int port) {
        portNumber = port;
        this.newClientQueue = newClientQueue;
    }

    public void init() throws InitialisationException {
        try {
            serverSocket = new ServerSocket(portNumber);

            serverSocket.setSoTimeout(LISTEN_TIMEOUT);
        } catch (IOException ex) {
            throw new InitialisationException("Couldn't create server socket", ex);
        }
    }

    public void run() {
        Socket clientSocket;

        while (running) {
            try {
                clientSocket = serverSocket.accept();
                logger.debug("got a connection from " + clientSocket.getInetAddress().getHostAddress());
            } catch (SocketTimeoutException e) {
                continue;
            } catch (IOException ex) {
                logger.error("got an IOException while trying to accept a connection : " + ex.toString());
                continue;
            }

            // queue this connection
            newClientQueue.add(clientSocket);
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
