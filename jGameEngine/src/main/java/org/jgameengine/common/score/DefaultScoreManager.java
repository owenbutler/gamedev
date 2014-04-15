package org.jgameengine.common.score;

import org.apache.log4j.Logger;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;

public class DefaultScoreManager
        implements ScoreManager {

    protected static final Logger logger = Logger.getLogger(DefaultScoreManager.class);

    protected Engine engine;

    protected int score;

    protected int[] scores = new int[7];

    public void initScore() {
    }

    public void show() {
    }

    public void hide() {
    }

    public void addScore(GenericScore newScore) {
        score += newScore.getPoints();
        if (score < 0) {
            score = 0;
        }

        logger.trace("score is : " + score);

        engine.addGameObject((GameObject) newScore);

        calculateScoreArray();

        updateDrawables();
    }

    protected void updateDrawables() {
    }

    protected void calculateScoreArray() {
        scores[0] = (score % 10);
        scores[1] = (score % 100) / 10;
        scores[2] = (score % 1000) / 100;
        scores[3] = (score % 10000) / 1000;
        scores[4] = (score % 100000) / 10000;
        scores[5] = (score % 1000000) / 100000;
        scores[6] = (score % 10000000) / 1000000;
    }

    public void resetScore() {
        score = 0;
        calculateScoreArray();
        updateDrawables();
    }

    public GenericScore getScore() {
        return new GenericScore() {

            public int getPoints() {
                return score;
            }
        };
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
