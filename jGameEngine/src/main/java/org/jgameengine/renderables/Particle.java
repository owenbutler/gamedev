package org.jgameengine.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public class Particle
        extends BaseDrawableGameObject {

    public Particle(float x, float y) {

        this(x, y, 0.6f);
    }

    public Particle(float x, float y, float fadeTime) {
        super(AssetConstants.gfx_particle, x, y, 1, 1);
        setScreenClipRemove(true);
        setFadeAndRemove(fadeTime);
    }

    public void think() {
        baseDrawableThink();
    }
}