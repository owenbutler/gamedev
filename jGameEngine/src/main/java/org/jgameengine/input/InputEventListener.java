package org.jgameengine.input;

public interface InputEventListener {

    void inputLeft(int magnitude);

    void inputRight(int magnitude);

    void inputUp(int magnitude);

    void inputDown(int magnitude);

    void mouseEvent(int x, int y);

    void button0Down();

    void button0Up();

    void button1Down();

    void button1Up();

    void button2Down();

    void button2Up();

    void button3Up();

    void button3Down();

    void button4Up();

    void button4Down();

    void button5Up();

    void button5Down();

    void button6Up();

    void button6Down();
}
