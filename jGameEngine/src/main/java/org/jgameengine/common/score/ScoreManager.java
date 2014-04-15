package org.jgameengine.common.score;

public interface ScoreManager {

    void initScore();

    void show();

    void hide();

    void addScore(GenericScore score);

    void resetScore();

    GenericScore getScore();
}
