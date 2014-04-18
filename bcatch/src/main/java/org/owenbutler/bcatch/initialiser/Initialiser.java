package org.owenbutler.bcatch.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.bcatch.logic.GameLogic;

public class Initialiser extends EngineInitialiserSupport {

    public void initialiseGame() {

        GameLogic gameLogic = new GameLogic(engine);

        engine.registerGameService("game", gameLogic);
        gameLogic.gameLoads();

    }
}
