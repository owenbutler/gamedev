package org.jgameengine.nineteenfourtysomething.logic;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.BackgroundCloud;

/**
 * Class used to generate the pieces of background that fly past the game board.
 * 
 * @author Owen Butler
 */
public class BackgroundGenerator {

    private Engine engine;
    private static final float CLOUND_GENERATION_INTERVAL = 0.5f;


    public BackgroundGenerator(Engine engine) {
        this.engine = engine;

        engine.getEventHandler().addEventInLoop(this, CLOUND_GENERATION_INTERVAL, CLOUND_GENERATION_INTERVAL, new Event() {
            public void trigger() {
                generateCloud();
            }
        });
    }


    private void generateCloud() {

        int size = RandomUtils.nextInt(500) + 300;
        
        BackgroundCloud cloud = new BackgroundCloud(RandomUtils.nextInt(640), -size);
        cloud.setWidth(size);
        cloud.setHeight(size);

        int yVel = RandomUtils.nextInt(200) + 100;
        int xVel = RandomUtils.nextInt(50) - 25;

        cloud.setVelX(xVel);
        cloud.setVelY(yVel);

        int rotation = RandomUtils.nextInt(10) - 5;
        cloud.setRotate(rotation);
        cloud.setRotation(RandomUtils.nextInt(360));

        cloud.setSortZ(GameConstants.Z_CLOUD);
        engine.addGameObject(cloud);
    }


    public void stop() {
        engine.getEventHandler().removeEventsForOwner(this);
    }
}
