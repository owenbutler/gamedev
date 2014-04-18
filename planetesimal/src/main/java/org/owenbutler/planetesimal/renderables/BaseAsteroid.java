package org.owenbutler.planetesimal.renderables;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.events.Event;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.planetesimal.constants.AssetConstants;
import org.owenbutler.planetesimal.constants.GameConstants;
import org.owenbutler.planetesimal.logic.PlanetesimalGameLogic;

public abstract class BaseAsteroid
        extends BaseDrawableGameObject {

    protected int health;

    protected int smokes;

    protected int dustSpawnMod;

    public BaseAsteroid(String surfaceName, float x, float y, float width, float height) {
        super(surfaceName, x, y, width, height);
        setScreenClipRemove(false);
        setScreenWrap(true);
        setHealth(1);

        setRotate(RandomUtils.nextInt(GameConstants.ASTEROID_ROTATION) - GameConstants.ASTEROID_ROTATION / 2);

        gameEngine.getEventHandler().addEventInLoop(this, GameConstants.ASTEROID_DUST_SPAWN_RATE, GameConstants.ASTEROID_DUST_SPAWN_RATE, new Event() {
            public void trigger() {
                spawnDust();
            }
        });

        setSortZ(GameConstants.Z_ASTEROID);

    }

    protected void damaged() {
        health--;

        if (health == 0) {
            setCollidable(false);
            killed();

            ((PlanetesimalGameLogic) gameEngine.getRegisteredGameService("game")).asteroidDies(this);
        } else {
            gameEngine.getAudioManager().loadOrGetSample(AssetConstants.snd_asteroidDamage).playSample();
        }
    }

    abstract protected void killed();

    public void collision(Collidable otherBody) {

        if (otherBody instanceof Player) {
            Player player = (Player) otherBody;
            player.damaged();
        } else if (otherBody instanceof PlayerBullet1) {
            damaged();
        }
    }

    public void destroy() {
        // set a looping event animate us
        gameEngine.getEventHandler().addEventInLoop(this, 0.05f, 0.05f, new Event() {
            public void trigger() {
                incrementAnimationAndRemove();
            }
        });
    }

    public void setVelocityFromParent(float velX, float velY) {

        setVelX(velX + RandomUtils.nextInt(GameConstants.ASTEROID_VEL_RANDOM) - (GameConstants.ASTEROID_VEL_RANDOM / 2));
        setVelY(velY + RandomUtils.nextInt(GameConstants.ASTEROID_VEL_RANDOM) - (GameConstants.ASTEROID_VEL_RANDOM / 2));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSmokes() {
        return smokes;
    }

    public void setSmokes(int smokes) {
        this.smokes = smokes;
    }

    public void gameOverRemove() {

        setFadeAndRemove(1.8f, 0.1f);
    }

    protected void spawnDust() {
        Particle particle = new Particle(x + (RandomUtils.nextInt(dustSpawnMod) - (dustSpawnMod / 2)), y + (RandomUtils.nextInt(dustSpawnMod) - (dustSpawnMod / 2)));
        gameEngine.addGameObject(particle);
    }
}
