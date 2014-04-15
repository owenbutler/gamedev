package org.owenbutler.grazier.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.grazier.constants.AssetConstants;

public class SmallRainbow
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public SmallRainbow(float x, float y) {
        super(AssetConstants.gfx_smallRainbow, x, y, 32, 32);

        setCollidable(false);

        setSortZ(AssetConstants.z_rainbow);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
    }

}