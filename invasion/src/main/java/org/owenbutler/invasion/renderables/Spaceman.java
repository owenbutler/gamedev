package org.owenbutler.invasion.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.invasion.constants.AssetConstants;

public class Spaceman extends BaseDrawableGameObject {

    public Spaceman(float x, float y) {
        super(AssetConstants.gfx_spaceman, x, y, 16.0f, 16.0f);

        setCollidable(true);
        setCollisionRadius(5);
        setScreenClipRemove(false);

        surface.enableAnimation(null, 2, 2);
        endFrame = 2;

        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

        setVelY(5.0f);

        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                setVelY(Math.abs(getVelY() * 1.1f));
            }
        });

        setRandomRotation();
        setRotate(9);

        setVelXYRandom(20);
    }

    public void think() {
        baseDrawableThink();

        if (y > 590) {
            explode();
        }
    }

    private void explode() {
        for (int i = 0; i < 20; ++i) {
            Blood particle = new Blood(x, y);
            particle.setVelXYRandom(50);

            gameEngine.addGameObject(particle);
        }
        removeSelf();
    }

    public void collision(Collidable otherBody) {
        if (otherBody instanceof BaseBrick) {
            explode();
        }
    }
}