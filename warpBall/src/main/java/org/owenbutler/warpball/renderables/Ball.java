package org.owenbutler.warpball.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.audio.SampleHandle;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.warpball.constants.AssetConstants;
import org.owenbutler.warpball.constants.GameConstants;
import org.owenbutler.warpball.logic.WarpBallLogic;

/**
 * The ball in our ponglike game.
 *
 * @author Owen Butler
 */
public class Ball
        extends BaseDrawableGameObject {

    private float speed = GameConstants.BALL_INITIAL_SPEED;

    private float speedIncrement = GameConstants.BALL_SPEED_INCREMENT;

    private float angle;

    public Ball(float x, float y) {
        super(AssetConstants.gfx_ball, x, y, 16, 16);
    }


    public void think() {

        if (getY() < 8) {
            setY(9);
            setVelY(-getVelY());
            // hit top
            playRandomBounceSound();
            incrementSpeed();
        } else if (getY() > 600 - 8) {
            setY(591);
            setVelY(-getVelY());
            // hit bottom
            playRandomBounceSound();
            incrementSpeed();
        }

        if (getX() < 0) {
            WarpBallLogic warpBallLogic = (WarpBallLogic) gameEngine.getRegisteredGameService("game");
            warpBallLogic.enemyScored();
        }
        if (getX() > 800) {
            WarpBallLogic warpBallLogic = (WarpBallLogic) gameEngine.getRegisteredGameService("game");
            warpBallLogic.playerScored();
        }


        baseDrawableThink();
    }


    private void playRandomBounceSound() {
        int num = RandomUtils.nextInt(3);

        SampleHandle sampleHandle;
        switch (num) {
            case 0:
                sampleHandle = gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce1);
                break;
            case 1:
                sampleHandle = gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce2);
                break;
            case 2:
                sampleHandle = gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce3);
                break;
            default:
                sampleHandle = gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_bounce1);
                break;
        }

        sampleHandle.playSample();
    }


    public void setInitialVelocity() {

        setVelocityFromAngleAndSpeed(setInitialRandomAngle());

//        float initialVelX = 300.0f;
//        float initialVelY = 200.0f;
//
//        if (RandomUtils.nextBoolean()) {
//            initialVelX = -initialVelX;
//        }
//
//        if (RandomUtils.nextBoolean()) {
//            initialVelY = -initialVelY;
//        }
//
//        setVelXY(initialVelX, initialVelY);
    }


    public void reset() {
        setVelXY(0, 0);
        setXY(400, 300);
    }


    public float setInitialRandomAngle() {

//        int initial = -45;
        int initial = RandomUtils.nextInt(90) - 45;

        if (RandomUtils.nextBoolean()) {
            initial = (initial + 180) % 360;
        }

        angle = initial;

        return initial;
    }

    public void reverseAngle() {

        angle = (int) ((angle + 180) % 360);
    }

    public void incrementSpeed() {
        speed += speedIncrement;
    }

    public void setVelocityFromAngleAndSpeed(float angle) {
        setVelX((float) Math.cos(Math.toRadians(angle)) * speed);
        setVelY((float) Math.sin(Math.toRadians(angle)) * speed);
    }


    public float getAngle() {
        return angle;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }


    public void resetSpeed() {
        speed = GameConstants.BALL_INITIAL_SPEED;
    }
}
