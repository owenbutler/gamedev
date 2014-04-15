package org.jgameengine.common;

public class InitialisationException extends RuntimeException {

    public InitialisationException(String string) {
        super(string);
    }

    public InitialisationException(String message, Throwable cause) {
        super(message, cause);
    }
}
