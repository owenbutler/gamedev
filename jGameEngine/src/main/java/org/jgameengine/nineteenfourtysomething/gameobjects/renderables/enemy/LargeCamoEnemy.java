package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class LargeCamoEnemy
        extends LargeEnemy {

    /**
     * Constructor.
     */
    public LargeCamoEnemy() {
        super();

        initLargeEnemy();

        startFrame = 0;
        endFrame = 2;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_LRGCAMO;

        initEnemyAnimation();
    }

    /**
     * Derived enemy ships should do logic here.
     */
    protected void enemyThink() {

    }

    /**
     * Entry point for enemies to give a bonus.
     * <p/>
     * By default, nothing happens.
     */
    protected void giveBonus() {
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_2k));
    }

}
