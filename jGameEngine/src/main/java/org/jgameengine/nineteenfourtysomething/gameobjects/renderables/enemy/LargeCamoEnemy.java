package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class LargeCamoEnemy
        extends LargeEnemy {

    public LargeCamoEnemy() {
        super();

        initLargeEnemy();

        startFrame = 0;
        endFrame = 2;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_LRGCAMO;

        initEnemyAnimation();
    }

    protected void enemyThink() {

    }

    protected void giveBonus() {
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_2k));
    }

}
