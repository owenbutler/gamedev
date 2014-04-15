package org.jgameengine.input;

public interface MovementListener {

    void inputLeft(int magnitude);

    void inputRight(int magnitude);

    void inputUp(int magnitude);

    void inputDown(int magnitude);
}
