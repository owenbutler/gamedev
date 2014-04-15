package org.owenbutler.JGE_ARTIFACT_ID.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.JGE_ARTIFACT_ID.logic.GameLogic;

public class Initialiser extends EngineInitialiserSupport {

    /**
     * called by the engine before the first frame.
     * <p/>
     * This is the spot where you would insert your custom game objects.
     */
    public void initialiseGame() {

        GameLogic gameLogic = new GameLogic(engine);

        engine.registerGameService("game", gameLogic);
        gameLogic.gameLoads();

    }
}
