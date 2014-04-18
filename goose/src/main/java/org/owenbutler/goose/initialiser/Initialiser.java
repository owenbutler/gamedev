package org.owenbutler.goose.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.goose.logic.GameLogic;

public class Initialiser extends EngineInitialiserSupport {

    public void initialiseGame() {

        GameLogic gameLogic = new GameLogic(engine);

        engine.registerGameService("game", gameLogic);
        gameLogic.gameLoads();

    }
}
