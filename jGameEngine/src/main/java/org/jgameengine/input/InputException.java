package org.jgameengine.input;

import org.lwjgl.LWJGLException;

public class InputException
        extends RuntimeException {

    public InputException(String message, LWJGLException cause) {
        super(message, cause);
    }
}
