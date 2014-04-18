package org.owenbutler.theta.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.theta.logic.GameLogic;

public class Initialiser extends EngineInitialiserSupport {

    public void initialiseGame() {

        GameLogic gameLogic = new GameLogic(engine);

        gameLogic.gameLoads();

    }
}
