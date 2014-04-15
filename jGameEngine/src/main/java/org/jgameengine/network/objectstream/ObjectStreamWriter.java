package org.jgameengine.network.objectstream;

import org.apache.log4j.Logger;
import org.jgameengine.network.NetworkException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStreamWriter {

    private static final Logger logger = Logger.getLogger(ObjectStreamReaderThread.class);

    private ObjectOutputStream outputStream;

    private Socket clientSocket;

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ObjectStreamWriter() {
        outputStream = null;
        clientSocket = null;
    }

    public void init() throws NetworkException {
        try {
            logger.debug("creating output stream");
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            logger.debug("finished creating output stream");
        } catch (IOException e) {
            throw new NetworkException("couldn't create connection", e);
        }
    }

    public void writeObject(Object object) throws NetworkException {
        try {
            long startTime = System.currentTimeMillis();
            outputStream.writeObject(object);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;

            if (logger.isTraceEnabled()) {
                logger.trace("elapsped time to write a packet was " + elapsedTime);
            }
        } catch (IOException e) {
            throw new NetworkException("couldn't write packet", e);
        }

    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            logger.warn("couldn't close down object output stream while closing stream writer", e);
        }
    }
}
