package org.jgameengine.network;

import org.jgameengine.common.InitialisationException;

import java.net.Socket;

public interface NetworkDriver {

    void initialise(Socket in_sock) throws InitialisationException;

    boolean objectAvailable();

    Object getNext();

    void writeObject(Object outgoing) throws NetworkException;

    void close();

    boolean isValid();
}
