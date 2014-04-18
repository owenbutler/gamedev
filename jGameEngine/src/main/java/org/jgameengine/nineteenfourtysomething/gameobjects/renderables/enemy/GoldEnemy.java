package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class GoldEnemy
        extends BaseEnemy {

    public GoldEnemy() {
        super();

        initSmallEnemy();

        startFrame = 16;
        endFrame = 18;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_GOLD;

        initEnemyAnimation();

        gameEngine.getEventHandler().addEventInLoop(this, (RandomUtils.nextFloat() + 0.1f) * EnemyShipConstants.GOLD_ENEMY_SHOOT_INTERVAL,
                EnemyShipConstants.GOLD_ENEMY_SHOOT_INTERVAL, new Event() {
            public void trigger() {
                if (!offScreen()) {
                    shootSimple();
                    shootSimple();
                    shootSimple();
                    gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGunMedium).playSample();
                }
            }
        });

    }

    protected void enemyThink() {

    }

    protected void giveBonus() {
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_100));
    }
}
