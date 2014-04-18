package org.owenbutler.planetesimal.renderables;

import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;

public class SmokePuff
        extends BaseDrawableGameObject {

    public SmokePuff(float x, float y) {
        super(AssetConstants.gfx_smokePuff, x, y, 16, 16);

        getSurface().enableAnimation(null, 4, 2);
        endFrame = 7;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });

        setRandomRotation();

        setSortZ(GameConstants.Z_EXPLOSION_SMOKE);
    }

    public void think() {
        baseDrawableThink();
    }
}
