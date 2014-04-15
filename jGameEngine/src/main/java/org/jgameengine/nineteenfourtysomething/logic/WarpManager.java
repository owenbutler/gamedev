package org.jgameengine.nineteenfourtysomething.logic;

import org.jgameengine.common.events.Event;
import org.jgameengine.engine.Engine;
import org.jgameengine.nineteenfourtysomething.constants.GameConstants;
import org.jgameengine.renderer.lwjgl.LWJGL2dRenderer;

public class WarpManager {

    private Engine engine;

    LWJGL2dRenderer renderer;

    public WarpManager(Engine engine) {
        this.engine = engine;

        renderer = (LWJGL2dRenderer) engine.getRenderer();
    }

    public void addWarp(int x, int y) {

        engine.getEventHandler().removeEventsForOwner(this);

        renderer.addWarp(x, y);

        engine.getEventHandler().addEventIn(this, GameConstants.WARP_TIME, new Event() {
            public void trigger() {
                renderer.removeWarp();
            }
        });
    }
}
