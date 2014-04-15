package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class MediumWaterPlaneEnemy extends MediumEnemy {

    /**
     * Constructor.
     */
    public MediumWaterPlaneEnemy() {
        super();

        initMediumEnemy();

        startFrame = 4;
        endFrame = 6;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_MEDWATERPLANE;

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
//        final int[] coinTypes = new int[] {Coin.TYPE_GOLD, Coin.TYPE_GREEN, Coin.TYPE_RED, Coin.TYPE_SILVER};

//        for (int i = 0; i < 5; ++i) {
//            gameEngine.addGameObject(new Coin(x, y, coinTypes[RandomUtils.nextInt(coinTypes.length)]));
//        }

        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_500));
    }
}

