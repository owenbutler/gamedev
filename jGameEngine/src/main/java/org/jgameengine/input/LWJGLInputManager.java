package org.jgameengine.input;

import org.apache.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class LWJGLInputManager
        implements InputManager {

    private static final Logger logger = Logger.getLogger(LWJGLInputManager.class.getName());

    private List<MovementListener> movementListeners = new ArrayList<MovementListener>();

    private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();

    private List<MouseListener> addedMouseListeners = new ArrayList<MouseListener>();
    private List<MouseListener> removedMouseListeners = new ArrayList<MouseListener>();

    private List<PauseListener> pauseListeners = new ArrayList<PauseListener>();

    private int mouseVerticalSize;

    private boolean paused = false;

    public void initInput() {

        try {
            Controllers.create();
            int numberOfControllers = Controllers.getControllerCount();

            logger.debug("found (" + numberOfControllers + ") controllers");

            for (int i = 0; i < numberOfControllers; i++) {
                Controller c = Controllers.getController(i);
                logger.debug("c.getName() = " + c.getName());
            }
        } catch (LWJGLException e) {
            throw new IllegalStateException(e);
        }

        // flush the mouse queue?
        // was seeing about 50 mouse button events before anything happend, run this loop to
        // absorb those events before the main game loop starts
        while (Mouse.next()) {
            // ignore event
        }

    }

    public void processInput() {
        processKeyboardInput();

        processMouseInput();

        processControllerInput();
    }

    private void processControllerInput() {

        while (Controllers.next()) {
            Controller source = Controllers.getEventSource();

            if (Controllers.isEventButton()) {
                int button = Controllers.getEventControlIndex();
                System.out.println("Event was from " + source.getName() + " and was from button " + Controllers.getEventControlIndex());
            }
            if (Controllers.isEventAxis()) {
                System.out.println("Event was from " + source.getName() + " and was from axis " + Controllers.getEventControlIndex());
            }
        }
    }

    private void processKeyboardInput() {
        Keyboard.poll();

        while (Keyboard.next()) {
            processKey();
        }
    }

    private void processMouseInput() {

        mouseListeners.addAll(addedMouseListeners);
        addedMouseListeners.clear();

        while (Mouse.next()) {
            int mouseEventX = Mouse.getEventX();
            int mouseEventY = mouseVerticalSize - Mouse.getEventY();

            int mouseButton = Mouse.getEventButton();

            for (MouseListener mouseListener : mouseListeners) {
                // if this is not a mouse button event, it must be a movement event, so call that
                if (mouseButton == -1) {
                    mouseListener.mouseEvent(mouseEventX, mouseEventY);
                } else {
                    boolean mouseButtonState = Mouse.getEventButtonState();

                    // decide which mouse button event to trigger
                    if (!mouseButtonState) {
                        switch (mouseButton) {
                            case 0:
                                mouseListener.button0Up();
                                break;
                            case 1:
                                mouseListener.button1Up();
                                break;
                            case 2:
                                mouseListener.button2Up();
                                break;
                            default:
                        }
                    } else {
                        switch (mouseButton) {
                            case 0:
                                mouseListener.button0Down();
                                break;
                            case 1:
                                mouseListener.button1Down();
                                break;
                            case 2:
                                mouseListener.button2Down();
                                break;
                            default:
                        }
                    }
                }
            }
        }

        mouseListeners.removeAll(removedMouseListeners);
        removedMouseListeners.clear();
    }

    private void processKey() {
        int key = Keyboard.getEventKey();
        boolean keyState = Keyboard.getEventKeyState();

        processMovementKey(key, keyState);
    }

    private boolean processMovementKey(int key, boolean keyState) {
        boolean foundKey = true;
        int magnitude = keyState ? 1 : 0;

        switch (key) {
            case Keyboard.KEY_W:
                inputUp(magnitude);
                break;
            case Keyboard.KEY_UP:
                inputUp(magnitude);
                break;
            case Keyboard.KEY_S:
                inputDown(magnitude);
                break;
            case Keyboard.KEY_DOWN:
                inputDown(magnitude);
                break;
            case Keyboard.KEY_A:
                inputLeft(magnitude);
                break;
            case Keyboard.KEY_LEFT:
                inputLeft(magnitude);
                break;
            case Keyboard.KEY_D:
                inputRight(magnitude);
                break;
            case Keyboard.KEY_RIGHT:
                inputRight(magnitude);
                break;
            case Keyboard.KEY_P:
                inputPause(magnitude);
                break;
            case Keyboard.KEY_Z:
                if (magnitude == 1) {

                }
            default:
                foundKey = false;
        }
        return foundKey;
    }

    private void inputPause(int magnitude) {

        if (magnitude == 1) {
            if (paused) {
                paused = false;
                for (PauseListener pauseListener : pauseListeners) {
                    pauseListener.unPause();
                }
            } else {
                for (PauseListener pauseListener : pauseListeners) {
                    pauseListener.pause();
                }
                paused = true;
            }
        }
    }

    private void inputUp(int magnitude) {
        for (MovementListener listener : movementListeners) {
            listener.inputUp(magnitude);
        }
    }

    private void inputDown(int magnitude) {
        for (MovementListener listener : movementListeners) {
            listener.inputDown(magnitude);
        }
    }

    private void inputLeft(int magnitude) {
        for (MovementListener listener : movementListeners) {
            listener.inputLeft(magnitude);
        }
    }

    private void inputRight(int magnitude) {
        for (MovementListener listener : movementListeners) {
            listener.inputRight(magnitude);
        }
    }

    public void addMovementListener(MovementListener listener) {
        movementListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        logger.debug("adding mouse listener " + listener);

        addedMouseListeners.add(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        removedMouseListeners.add(listener);
    }

    public void addPauseListener(PauseListener listener) {
        pauseListeners.add(listener);
    }

    public void removePauseListener(PauseListener listener) {
        pauseListeners.remove(listener);
    }

    public void addInputEventListener(InputListener listener) {
    }

    public void removeInputEventListener(InputListener listener) {
    }

    public int getMouseVerticalSize() {
        return mouseVerticalSize;
    }

    public void setMouseVerticalSize(int mouseVerticalSize) {
        this.mouseVerticalSize = mouseVerticalSize;
    }
}
