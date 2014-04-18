package org.owenbutler.planetesimal.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;

public class LargeExplosion
        extends BaseDrawableGameObject {

    public LargeExplosion(float x, float y) {
        super(AssetConstants.gfx_largeExplosion, x, y, 64, 64);

        getSurface().enableAnimation(null, 8, 8);
        endFrame = 63;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.02f, 0.02f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });

        setRandomRotation();

        setSortZ(GameConstants.Z_EXPLOSION_BIG);
    }


    public void think() {
        baseDrawableThink();
    }
}