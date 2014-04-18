package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class BlueEnemy
        extends BaseEnemy {

    public BlueEnemy() {
        super();

        initSmallEnemy();

        startFrame = 11;
        endFrame = 13;
        frame = startFrame;

        health = EnemyShipConstants.HEALTH_BLUE;

        initEnemyAnimation();

        gameEngine.getEventHandler().addEventInLoop(this, (RandomUtils.nextFloat() + 0.1f) * EnemyShipConstants.LIGHT_GREEN_ENEMY_SHOOT_INTERVAL,
                EnemyShipConstants.LIGHT_GREEN_ENEMY_SHOOT_INTERVAL, new Event() {
            public void trigger() {
                if (!offScreen()) {
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
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_75));
    }
}
