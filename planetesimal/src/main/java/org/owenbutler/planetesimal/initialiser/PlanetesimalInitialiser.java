package org.owenbutler.planetesimal.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.planetesimal.logic.PlanetesimalGameLogic;

public class PlanetesimalInitialiser
        extends EngineInitialiserSupport {

    public void initialiseGame() {

        PlanetesimalGameLogic planetesimalGameLogic = new PlanetesimalGameLogic(engine);
        engine.registerGameService("game", planetesimalGameLogic);
        planetesimalGameLogic.gameStarts();

        System.out.println("loading music and playing");
        engine.getAudioManager().loadMusicFromURI("sfx/bgm.ogg").playStream();
        System.out.println("finished loading music");
    }
}
