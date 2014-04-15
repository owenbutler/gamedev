package org.owenbutler.warpball.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.owenbutler.warpball.constants.AssetConstants;
import org.owenbutler.warpball.renderables.Ball;

public class WarpBallLogic {

    protected Engine engine;

    protected boolean gameStarted = false;

    protected int playerScore = 0;
    protected int enemyScore;

    protected Ball ball;

    public WarpBallLogic(Engine engine) {
        this.engine = engine;

        ((WarpballHudManager) engine.getHudManager()).showIntroScreen();
    }

    public void playerPressedButton() {

        ((WarpballHudManager) engine.getHudManager()).hideIntroScreen();

        if (!gameStarted) {
            gameStarted = true;

            // start the ball rolling
            getBall().setInitialVelocity();

            engine.getAudioManager().loadOrGetSample(AssetConstants.snd_startGame).playSample();
        }
    }

    private void scored() {

        if (wonGame()) {
            resetScores();
        }
        resetBall();
        getBall().resetSpeed();
        updateScore();
        resetGame();
    }

    private void resetScores() {
        enemyScore = 0;
        playerScore = 0;
    }

    private boolean wonGame() {
        if (enemyScore > 9) {
            // enemy won the game
            engine.getAudioManager().loadOrGetSample(AssetConstants.snd_death).playSample();
            ((WarpballHudManager) engine.getHudManager()).showIntroScreen();
            return true;
        }

        if (playerScore > 9) {
            // player won the game
            engine.getAudioManager().loadOrGetSample(AssetConstants.snd_gameOver).playSample();
            ((WarpballHudManager) engine.getHudManager()).showIntroScreen();
            return true;
        }

        return false;
    }

    public void enemyScored() {

        enemyScore++;
        scored();

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_losePoint).playSample();
    }

    public void playerScored() {

        playerScore++;
        scored();

        engine.getAudioManager().loadOrGetSample(AssetConstants.snd_winPoint).playSample();
    }

    private void resetGame() {
        engine.getEventHandler().addEventIn(this, 1.0f, new Event() {
            public void trigger() {
                gameStarted = false;
                getBall().setBlRender(true);
            }
        });
    }

    private void updateScore() {
        WarpballHudManager warpBallHudManager = (WarpballHudManager) engine.getHudManager();
        warpBallHudManager.updateScores(playerScore, enemyScore);
    }

    private void resetBall() {
        Ball ball = getBall();
        ball.reset();
        ball.setBlRender(false);
    }

    public Ball getBall() {
        if (ball == null) {
            ball = (Ball) engine.getRegisteredGameService("ball");
        }
        return ball;
    }

}
