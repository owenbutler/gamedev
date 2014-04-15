package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.nineteenfourtysomething.constants.EnemyShipConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.Score;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class SmallBomberEnemy
        extends BaseEnemy {

    /**
     * create a new Small bomber enemy.
     */
    public SmallBomberEnemy() {
        super();

        initSmallEnemy();

        health = EnemyShipConstants.HEALTH_SMALLBOMBER;

        getSurface().selectAnimationFrame(24);

        gameEngine.getEventHandler().addEventInLoop(this, (RandomUtils.nextFloat() + 0.1f),
                EnemyShipConstants.SMALL_BOMBER_ENEMY_SHOOT_INTERVAL, new Event() {
            public void trigger() {
                if (!offScreen()) {
                    shootSimple();
                    gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_enemyGunMedium).playSample();
                }
            }
        });

    }

    protected void enemyThink() {

    }

    /**
     * Entry point for enemies to give a bonus.
     * <p/>
     * By default, nothing happens.
     */
    protected void giveBonus() {
        gameEngine.getScoreManager().addScore(new Score(x, y, Score.SCORE_250));
    }
}
