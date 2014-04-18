package org.jgameengine.network.objectstream;

import org.apache.log4j.Logger;
import org.jgameengine.common.InitialisationException;
import org.jgameengine.network.NetworkDriver;
import org.jgameengine.network.NetworkException;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectStreamNetworkDriver
        implements NetworkDriver, ConnectionFailListener {

    private static final Logger logger = Logger.getLogger(ObjectStreamNetworkDriver.class);

    private ConcurrentLinkedQueue<Object> queueIncoming;

    private ObjectStreamReaderThread readerThread;
    private ObjectStreamWriter writer;

    private Socket clientSocket;

    private boolean connectionValid = true;

    private boolean initialised = false;

    public ObjectStreamNetworkDriver() {
        queueIncoming = new ConcurrentLinkedQueue<>();
    }

    public void initialise(Socket socket) throws InitialisationException {
        clientSocket = socket;

        readerThread = new ObjectStreamReaderThread();
        writer = new ObjectStreamWriter();
        writer.setClientSocket(clientSocket);
        try {
            writer.init();
        } catch (NetworkException e) {
            throw new InitialisationException("error initialising object stream writer", e);
        }

        readerThread.setIncomingQueue(queueIncoming);
        readerThread.setClientSocket(socket);
        readerThread.setConnectionFailListener(this);

        readerThread.start();

        initialised = true;
    }

    public boolean objectAvailable() {
        return (!queueIncoming.isEmpty());
    }

    public Object getNext() {
        return queueIncoming.remove();
    }

    public void writeObject(Object in_outgoing) {
        if (isValid()) {
            try {
                writer.writeObject(in_outgoing);
            } catch (NetworkException e) {
                connectionFailed();
            }
        }
    }

    public void close() {

        if (!initialised) {
            return;
        }

        writer.close();
        readerThread.shutdown();

        try {
            readerThread.join();
        } catch (InterruptedException e) {
            logger.error("while waiting for reader thread to die", e);
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.error("closing socket", e);
        }
    }

    public boolean isValid() {
        boolean connectionValid;

        synchronized (this) {
            connectionValid = this.connectionValid;
        }

        return connectionValid;
    }

    public void connectionFailed() {
        synchronized (this) {
            connectionValid = false;
        }
    }
}
