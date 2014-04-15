package org.owenbutler.invasion.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.invasion.constants.AssetConstants;

public class Planet
        extends BaseDrawableGameObject {

    /**
     * create a background.
     */
    public Planet() {
        super(AssetConstants.gfx_planet, 400, 600 - 32, 800, 64);
        setScreenClipRemove(false);

        setSortZ(-1000);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
    }
}