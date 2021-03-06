package org.owenbutler.grazier.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.grazier.logic.GameLogic;

public class Initialiser extends EngineInitialiserSupport {

    public void initialiseGame() {

        GameLogic gameLogic = new GameLogic(engine);

        engine.registerGameService("game", gameLogic);
        gameLogic.gameLoads();

    }
}
