package org.owenbutler.warpball.initialiser;

import org.jgameengine.engine.EngineInitialiserSupport;
import org.owenbutler.warpball.logic.WarpBallLogic;
import org.owenbutler.warpball.renderables.Background;
import org.owenbutler.warpball.renderables.Ball;
import org.owenbutler.warpball.renderables.EnemyPaddle;
import org.owenbutler.warpball.renderables.PlayerPaddle;

public class WarpballInitialiser
        extends EngineInitialiserSupport {

    /**
     * called by the engine before the first frame.
     * <p/>
     * This is the spot where you would insert your custom game objects.
     */
    public void initialiseGame() {

        engine.addGameObject(new Background());

        WarpBallLogic warpBallLogic = new WarpBallLogic(engine);
        engine.registerGameService("game", warpBallLogic);

        Ball ball = new Ball(400, 300);
        engine.registerGameService("ball", ball);
        engine.addGameObject(ball);

        PlayerPaddle playerPaddle = new PlayerPaddle(32, 300);
        engine.getInputManager().addMouseListener(playerPaddle);
        engine.registerGameService("playerPaddle", playerPaddle);
        engine.addGameObject(playerPaddle);

        EnemyPaddle paddle2 = new EnemyPaddle(800 - 32, 300);
        engine.registerGameService("enemyPaddle", paddle2);
        engine.addGameObject(paddle2);

        System.out.println("loading music and playing");
        engine.getAudioManager().loadMusicFromURI("sfx/bgm.ogg").playStream();
        System.out.println("finished loading music");
    }
}
