package org.owenbutler.invasion.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.invasion.logic.InvasionGameLogic;

public class InvasionInitialiser extends EngineInitialiserSupport {

    public void initialiseGame() {

        InvasionGameLogic invasionGameLogic = new InvasionGameLogic(engine);

        engine.registerGameService("game", invasionGameLogic);
        invasionGameLogic.gameLoads();
    }
}
