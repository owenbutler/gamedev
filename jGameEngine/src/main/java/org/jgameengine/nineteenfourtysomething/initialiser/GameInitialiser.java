package org.jgameengine.nineteenfourtysomething.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.jgameengine.nineteenfourtysomething.logic.DefaultGameFlowLogic;
import org.jgameengine.nineteenfourtysomething.logic.GameFlowLogic;
import org.jgameengine.nineteenfourtysomething.logic.WarpManager;

public class GameInitialiser
        extends EngineInitialiserSupport {

    /**
     * called by the engine before the first frame.
     * <p/>
     * This is the spot where you would insert your custom game objects.
     */
    public void initialiseGame() {

        // register a hook into the special effects
        WarpManager warpManager = new WarpManager(engine);
        engine.registerGameService("warpManager", warpManager);

        // register our game logic service
        GameFlowLogic gameFlow = new DefaultGameFlowLogic(engine);
        engine.registerGameService("gameFlowLogic", gameFlow);

        // call it to start the proceedings...
        gameFlow.gameLoads();
    }

}
