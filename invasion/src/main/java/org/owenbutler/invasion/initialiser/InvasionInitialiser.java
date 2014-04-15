package org.owenbutler.invasion.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.invasion.logic.InvasionGameLogic;

public class InvasionInitialiser extends EngineInitialiserSupport {

    /**
     * called by the engine before the first frame.
     * <p/>
     * This is the spot where you would insert your custom game objects.
     */
    public void initialiseGame() {

        InvasionGameLogic invasionGameLogic = new InvasionGameLogic(engine);

        engine.registerGameService("game", invasionGameLogic);
        invasionGameLogic.gameLoads();
    }
}
