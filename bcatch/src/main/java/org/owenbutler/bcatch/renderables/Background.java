package org.owenbutler.bcatch.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.renderer.lwjgl.LWJGLSurface2d;
import org.owenbutler.bcatch.constants.AssetConstants;

import java.nio.FloatBuffer;

public class Background
        extends BaseDrawableGameObject {

    protected float yTexMod;

    LWJGLSurface2d lwSurface2d;

    public float defaultYTexSpeed = 0.2f;

    public Background() {
        super(AssetConstants.gfx_background, 400, 300, 800, 600);

        setSortZ(-10);

        surface.enableAnimation(null, 1, 1);
        surface.selectAnimationFrame(0);

        lwSurface2d = (LWJGLSurface2d) surface;

        yTexMod = defaultYTexSpeed;
    }

    public void think() {
        baseDrawableThink();

        updateTex();
    }

    private void updateTex() {

        FloatBuffer buffer = lwSurface2d.animatedInterleavedArrays[0];

        float amount = gameEngine.getTimeDelta() * -yTexMod;

        int[] indexes = {1, 6, 11, 16};
        for (int index : indexes) {

            float currentVal = buffer.get(index);
            buffer.put(index, currentVal + amount);
        }

    }

    public float getYTexMod() {
        return yTexMod;
    }

    public void setYTexMod(float yTexMod) {
        this.yTexMod = yTexMod;
    }
}