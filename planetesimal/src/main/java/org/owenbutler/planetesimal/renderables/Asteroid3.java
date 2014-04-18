package org.owenbutler.planetesimal.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.owenbutler.planetesimal.constants.AssetConstants;

public class Asteroid3
        extends BaseAsteroid {

    public Asteroid3(float x, float y) {
        super(AssetConstants.gfx_debri1, x, y, 16, 16);

        getSurface().enableAnimation(null, 4, 2);

        frame = RandomUtils.nextInt(5);
        getSurface().selectAnimationFrame(frame);
        endFrame = 5;

        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.1f, 0.1f, new Event() {
            public void trigger() {
                incrementAnimation();
            }
        });

        setRandomRotation();

//        setSortZ(GameConstants.Z_EXPLOSION_DEBRI);
        setHealth(1);
        setSmokes(0);

        setCollidable(true);
        setCollisionRadius(3.0f);

        dustSpawnMod = 10;

    }

    public void think() {
        baseDrawableThink();
    }

    protected void killed() {
        removeSelf();

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_asteroidDamage).playSample();
    }
}