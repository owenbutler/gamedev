package org.owenbutler.theta.logic;

import org.jgameengine.engine.Engine;

/**
 * The concept of a "level" in the game.
 *
 * @author Owen Butler
 */
public interface Level {

    void start();

    void end();

    void killLevel();

    void setEngine(Engine engine);

    void setLevelEndListener(LevelEndListener listener);
}
