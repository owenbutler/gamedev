package org.jgameengine.input;

public interface InputListener {

    void inputLeft(int magnitude);

    void inputRight(int magnitude);

    void inputUp(int magnitude);

    void inputDown(int magnitude);

    void cursorMoved(int x, int y);

    void button0Down();

    void button0Up();

    void button1Down();

    void button1Up();

    void button2Down();

    void button2Up();
}
