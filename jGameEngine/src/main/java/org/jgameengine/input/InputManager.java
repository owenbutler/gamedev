package org.jgameengine.input;

public interface InputManager {

    void initInput();

    void processInput();

    void addMovementListener(MovementListener listener);

    void addMouseListener(MouseListener listener);

    void removeMouseListener(MouseListener listener);

    void addPauseListener(PauseListener listener);

    void removePauseListener(PauseListener listener);

    void addInputEventListener(InputListener listener);

    void removeInputEventListener(InputListener listener);
}
