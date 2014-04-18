package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class MediumWaterPlaneEnemy extends MediumEnemy {

    public MediumWaterPlaneEnemy() {
        super();

        initMediumEnemy();

        startFrame = 4;
        endFrame = 6;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_MEDWATERPLANE;

        initEnemyAnimation();
    }

    protected void enemyThink() {

    }

    protected void giveBonus() {
//        final int[] coinTypes = new int[] {Coin.TYPE_GOLD, Coin.TYPE_GREEN, Coin.TYPE_RED, Coin.TYPE_SILVER};

//        for (int i = 0; i < 5; ++i) {
//            gameEngine.addGameObject(new Coin(x, y, coinTypes[RandomUtils.nextInt(coinTypes.length)]));
//        }

        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_500));
    }
}

