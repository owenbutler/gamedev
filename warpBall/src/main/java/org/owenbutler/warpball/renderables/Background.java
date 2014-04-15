package org.owenbutler.warpball.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.warpball.constants.AssetConstants;

/**
 * The paddle in our ponglike game.
 *
 * @author Owen Butler
 */
public class Background
        extends BaseDrawableGameObject {

    /**
     * constructor.
     *
     */
    public Background() {
        super(AssetConstants.gfx_background, 400, 300, 800, 600);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {

        baseDrawableThink();
    }
}