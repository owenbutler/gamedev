package org.owenbutler.theta.logic;

import org.jgameengine.engine.Engine;

public interface Level {

    void start();

    void end();

    void killLevel();

    void setEngine(Engine engine);

    void setLevelEndListener(LevelEndListener listener);
}
