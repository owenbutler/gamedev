package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class LargeBomberEnemy
        extends LargeEnemy {

    /**
     * Constructor.
     */
    public LargeBomberEnemy() {
        super();

        initLargeEnemy();

        health = EnemyShipConstants.HEALTH_LRGBOMBER;

        getSurface().selectAnimationFrame(3);
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
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_1k));
    }

}

