package org.jgameengine.nineteenfourtysomething.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.GameObject;
import org.jgameengine.engine.Engine;
import org.jgameengine.engine.eventlistener.EngineEventListener;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BaseEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.BlueEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.GoldEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.GreenEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.LargeBomberEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.LargeCamoEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.LightBlueEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.LightGreenEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.MediumGhostEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.MediumGreenEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.MediumWaterPlaneEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.SmallBomberEnemy;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy.WhiteEnemy;

public class WaveGenerator implements EngineEventListener {

    private static final Logger logger = Logger.getLogger(WaveGenerator.class.getName());

    private Engine engine;

    private int wave = 10;

    private int waveLiving = 0;

    private GameFlowLogic gameFlowLogic;

    public WaveGenerator(Engine engine, GameFlowLogic logic) {
        this.engine = engine;
        gameFlowLogic = logic;

        engine.getEventHandler().addEventInLoop(this, 1.0f, 2.0f, new Event() {
            public void trigger() {
                checkWave();
            }
        });
    }

    private void checkWave() {

        if (waveLiving <= 0) {
            waveLiving = 0;
            wave++;

            for (int i = 0; i < wave; ++i) {
                spawnShip();
                ++waveLiving;
            }
        }
    }

    private void spawnShip() {
        int num = RandomUtils.nextInt(12);

        switch (num) {
            case 0:
                engine.addGameObject(new GreenEnemy());
                break;
            case 1:
                engine.addGameObject(new WhiteEnemy());
                break;
            case 2:
                engine.addGameObject(new LightGreenEnemy());
                break;
            case 3:
                engine.addGameObject(new BlueEnemy());
                break;
            case 4:
                engine.addGameObject(new GoldEnemy());
                break;
            case 5:
                engine.addGameObject(new LightBlueEnemy());
                break;
            case 6:
                engine.addGameObject(new SmallBomberEnemy());
                break;
            case 7:
                engine.addGameObject(new MediumGreenEnemy());
                break;
            case 8:
                engine.addGameObject(new MediumWaterPlaneEnemy());
                break;
            case 9:
                engine.addGameObject(new MediumGhostEnemy());
                break;
            case 10:
                engine.addGameObject(new LargeBomberEnemy());
                break;
            case 11:
                engine.addGameObject(new LargeCamoEnemy());
                break;
            default:
                break;
        }

    }

    public void stop() {
        engine.getEventHandler().removeEventsForOwner(this);
    }

    /**
     * Hook for when gameobjects are added to the world.
     *
     * @param added the gameobject which was added
     */
    public void gameObjectAdded(GameObject added) {
    }

    /**
     * Hook for when gameobjects are removed from the world.
     *
     * @param removed removed
     */
    public void gameObjectRemoved(GameObject removed) {
        if (removed instanceof BaseEnemy) {
            --waveLiving;
            logger.trace("number left living is: " + waveLiving);
            gameFlowLogic.enemyDies((BaseEnemy) removed);
        }
    }

    /**
     * Called just before a frame starts processing.
     */
    public void frameStart() {
    }

    /**
     * Called just after a frame has finished processing.
     */
    public void frameFinish() {
    }
}
