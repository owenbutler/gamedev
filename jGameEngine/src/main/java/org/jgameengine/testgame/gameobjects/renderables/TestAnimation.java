package org.jgameengine.testgame.gameobjects.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class TestAnimation
        extends BaseDrawableGameObject {

    int frame = 0;

    public TestAnimation(float x, float y) {
        super("animationTest2.png", x, y, 32, 32);
//        setScreenClipRemove(true);

        // set ourselves to start fading out in 10 seconds
        // fade for 5 seconds, then remove
        setFadeAndRemove(10, 5);

        getSurface().enableAnimation(null, 3, 3);

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.20f, 0.2f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

    }

    public void incrementAnimation() {
        getSurface().selectAnimationFrame(++frame % 9);
    }

    public void think() {
        baseDrawableThink();
    }
}
