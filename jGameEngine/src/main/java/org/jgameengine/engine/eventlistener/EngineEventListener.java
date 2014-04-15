package org.jgameengine.engine.eventlistener;

import org.jgameengine.common.gameobjects.GameObject;

public interface EngineEventListener {

    void gameObjectAdded(GameObject added);

    void gameObjectRemoved(GameObject removed);

    void frameStart();

    void frameFinish();
}
