package org.owenbutler.warpball.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.audio.SampleHandle;
import org.jgameengine.collision.CollisionDetectionHandler;
import org.jgameengine.common.gameobjects.Collidable;
import org.jgameengine.engine.Engine;
import org.owenbutler.warpball.constants.AssetConstants;
import org.owenbutler.warpball.renderables.Ball;
import org.owenbutler.warpball.renderables.EnemyPaddle;
import org.owenbutler.warpball.renderables.PlayerPaddle;

import java.util.Set;

public class WarpBallCollisionDetection
        implements CollisionDetectionHandler {

    protected Engine engine;

    public void doCollisionDetection(Set gameObjects) {

        // get the player paddle
        PlayerPaddle playerPaddle = (PlayerPaddle) engine.getRegisteredGameService("playerPaddle");

        // get the enemy paddle
        EnemyPaddle enemyPaddle = (EnemyPaddle) engine.getRegisteredGameService("enemyPaddle");

        // get the ball
        Ball ball = (Ball) engine.getRegisteredGameService("ball");

        // check whether the ball has hit the player paddle
        float ballX = ball.getX();
        float ballY = ball.getY();

        float playerPaddleX = playerPaddle.getX();
        float playerPaddleY = playerPaddle.getY();

        if ((ballX - 8) < (playerPaddleX + 16)) {
            if (ballY > (playerPaddleY - 56) && ballY < (playerPaddleY + 56)) {
                ballCollidedPlayer();

                float newAngle = getAngleFromDifference(ballY, playerPaddleY);
                ball.incrementSpeed();
                ball.reverseAngle();
                ball.setVelocityFromAngleAndSpeed(ball.getAngle() + newAngle);
                ball.setX(playerPaddleX + 25);

                return;
            }
        }

        float enemyPaddleX = enemyPaddle.getX();
        float enemyPaddleY = enemyPaddle.getY();

        if (ballX > (enemyPaddleX - 8)) {
            if (ballY > (enemyPaddleY - 56) && ballY < (enemyPaddleY + 56)) {
                ballCollidedEnemy();

                float newAngle = getAngleFromDifference(ballY, enemyPaddleY);
                ball.incrementSpeed();
                ball.reverseAngle();
                ball.setVelocityFromAngleAndSpeed(ball.getAngle() + newAngle);
                ball.setX(enemyPaddleX - 25);
            }
        }

    }

    public void collideableAdded(Collidable newCollidable) {

    }

    public void collideableRemoved(Collidable oldCollidable) {

    }

    private float getAngleFromDifference(float ballY, float playerPaddleY) {

        return (ballY - playerPaddleY) * 1.0f;
    }

    private void ballCollidedEnemy() {
        playRandomBounceSound();
    }

    private void ballCollidedPlayer() {
        playRandomBounceSound();
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    private void playRandomBounceSound() {
        int num = RandomUtils.nextInt(3);

        SampleHandle sampleHandle;
        switch (num) {
            case 0:
                sampleHandle = engine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce1);
                break;
            case 1:
                sampleHandle = engine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce2);
                break;
            case 2:
                sampleHandle = engine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce3);
                break;
            default:
                sampleHandle = engine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce1);
                break;
        }

        sampleHandle.playSample();
    }

}
