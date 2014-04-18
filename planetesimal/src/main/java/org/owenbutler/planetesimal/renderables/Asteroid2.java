package org.owenbutler.planetesimal.renderables;

import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;

public class Asteroid2
        extends BaseAsteroid {

    public Asteroid2(float x, float y) {
        super(AssetConstants.gfx_asteroid1, x, y, GameConstants.ASTEROID2_WIDTH, GameConstants.ASTEROID2_HEIGHT);

        setCollidable(true);

        setCollisionRadius(12.0f);

        setRandomRotation();

        getSurface().enableAnimation(null, 4, 4);
        getSurface().selectAnimationFrame(0);
        endFrame = 10;

        setHealth(GameConstants.ASTEROID2_HEALTH);
        setSmokes(1);

        dustSpawnMod = 28;

    }

    public void think() {

        baseDrawableThink();
    }

    protected void killed() {
        destroy();

        for (int i = 0; i < GameConstants.ASTEROID2_DEBRI; ++i) {
            Asteroid3 asteroid3 = new Asteroid3(x, y);
            asteroid3.setVelocityFromParent(velX, velY);
            gameEngine.addGameObject(asteroid3);
        }

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_asteroidDestroy).playSample();
    }
}