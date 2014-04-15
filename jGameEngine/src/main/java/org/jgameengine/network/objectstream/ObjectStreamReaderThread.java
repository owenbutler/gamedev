package org.jgameengine.network.objectstream;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectStreamReaderThread
        extends Thread {

    private static final Logger logger = Logger.getLogger(ObjectStreamReaderThread.class);

    private ObjectInputStream inputStream;
    private ConcurrentLinkedQueue<Object> incomingQueue;
    private boolean blAlive;
    private Socket clientSocket;

    ConnectionFailListener connectionFailListener;

    public ObjectStreamReaderThread() {
        this.inputStream = null;
        incomingQueue = null;
        blAlive = true;
        clientSocket = null;
    }

    public ObjectStreamReaderThread(String name) {
        super(name);
        this.inputStream = null;
        incomingQueue = null;
        blAlive = true;
        clientSocket = null;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setIncomingQueue(ConcurrentLinkedQueue<Object> incomingQueue) {
        this.incomingQueue = incomingQueue;
    }

    public void run() {
        Object tempObject;
        boolean blValid = true;
        blAlive = true;

        // check that we are initialised
        if (clientSocket == null || incomingQueue == null) {
            logger.error("tried to run without being initialised, fatal, exiting");
            System.exit(-1);
        }

        // create the InputStream from the socket
        try {
            logger.debug("about to create object input stream for client");
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            logger.debug("finished creating object input stream for client");
        } catch (IOException e) {
            logger.error("ObjectStreamReaderThread.run: couldn't create ObjectInputStream");
            blAlive = false;
        }

        while (blAlive && blValid) {
            try {
                tempObject = inputStream.readObject();
                incomingQueue.add(tempObject);
            } catch (IOException e) {
                logger.warn("got an IOException while trying to read and object : " + e.getMessage());
                blValid = false;
            } catch (ClassNotFoundException e) {
                logger.error("got a ClassNotFoundException while trying to read an object : " + e.getMessage());
                e.printStackTrace();
            }

        }

        try {
            inputStream.close();
        } catch (IOException e) {
            logger.error("while shutting down thread, couldn't close ouput stream");
        }
    }

    public void shutdown() {
        this.blAlive = false;
    }

    public void setConnectionFailListener(ConnectionFailListener connectionFailListener) {
        this.connectionFailListener = connectionFailListener;
    }
}
