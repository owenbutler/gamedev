package org.owenbutler.warpball.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.warpball.constants.AssetConstants;

public class Background
        extends BaseDrawableGameObject {

    public Background() {
        super(AssetConstants.gfx_background, 400, 300, 800, 600);
    }


    public void think() {

        baseDrawableThink();
    }
}