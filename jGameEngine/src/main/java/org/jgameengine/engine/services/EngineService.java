package org.jgameengine.engine.services;

public interface EngineService {

    boolean needsInit();

    void init();

    boolean needsThinkEveryFrame();

    void serviceThink();
}
