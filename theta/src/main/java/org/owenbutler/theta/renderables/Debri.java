package org.owenbutler.theta.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.owenbutler.theta.constants.AssetConstants;

public class Debri
        extends BaseDrawableGameObject {

    /**
     * create a new debri.
     *
     * @param x x position
     * @param y y position
     */
    public Debri(float x, float y) {
        super(AssetConstants.gfx_debri1, x, y, 2, 8);
        setScreenClipRemove(true);

        int num = RandomUtils.nextInt(4) + 1;
        surface.initSurface("gfx/debri" + num + ".png", x, y, 2, 8);

        setRandomRotation();

        int rotAmount = RandomUtils.nextInt(500) + 200;
        setRotate(rotAmount);
        if (RandomUtils.nextBoolean()) {
            setRotate(rotAmount);
        } else {
            setRotate(-rotAmount);
        }

        setCollidable(false);

        setSortZ(AssetConstants.Z_DEBRI);

        setFadeAndRemove(1.0f);
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }

}