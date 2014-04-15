package org.owenbutler.grazier.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.grazier.constants.AssetConstants;

public class Flower
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet 1.
     *
     * @param x x position
     * @param y y position
     */
    public Flower(float x, float y) {
        super(AssetConstants.gfx_enemyDebri, x, y, 128, 128);

        String surface = "gfx/realFlower" + (RandomUtils.nextInt(5) + 1) + ".png";
        initSurface(surface, 128, 128);

        setScreenClipRemove(true);

        setCollidable(false);

        setRotate(RandomUtils.nextInt(100) - 50);

        setSortZ(AssetConstants.z_flower);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

}