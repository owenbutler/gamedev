package org.owenbutler.planetesimal.renderables;

import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;

public class Asteroid1
        extends BaseAsteroid {

    /**
     * constructor.
     *
     * @param x x position
     * @param y y position
     */
    public Asteroid1(float x, float y) {
        super(AssetConstants.gfx_asteroid1, x, y, GameConstants.ASTEROID1_WIDTH, GameConstants.ASTEROID1_HEIGHT);

        setCollidable(true);

        setCollisionRadius(25.0f);

        setRandomRotation();

        getSurface().enableAnimation(null, 4, 4);
        getSurface().selectAnimationFrame(0);
        endFrame = 10;

        setHealth(GameConstants.ASTEROID1_HEALTH);

        setSmokes(3);

        dustSpawnMod = 50;
    }

    /**
     * Run a frame of think time.
     */
    public void think() {

        baseDrawableThink();
    }

    protected void killed() {
        destroy();

        for (int i = 0; i < GameConstants.ASTEROID1_DEBRI; ++i) {
            Asteroid2 asteroid2 = new Asteroid2(x, y);
            asteroid2.setVelocityFromParent(velX, velY);
            gameEngine.addGameObject(asteroid2);
        }

        gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_asteroidDestroy).playSample();
    }
}
