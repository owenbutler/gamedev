package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;

public class MediumGreenEnemy extends MediumEnemy {

    /**
     * Constructor.
     */
    public MediumGreenEnemy() {
        super();

        initMediumEnemy();

        startFrame = 0;
        endFrame = 2;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_MEDGREEN;

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
//
//        for (int i = 0; i < 5; ++i) {
//            gameEngine.addGameObject(new Coin(x, y, coinTypes[RandomUtils.nextInt(coinTypes.length)]));
//        }

        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_350));
    }

}
