package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class LargeBomberEnemy
        extends LargeEnemy {

    public LargeBomberEnemy() {
        super();

        initLargeEnemy();

        health = EnemyShipConstants.HEALTH_LRGBOMBER;

        getSurface().selectAnimationFrame(3);
    }

    protected void enemyThink() {

    }

    protected void giveBonus() {
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_1k));
    }

}

