package org.jgameengine.testgame.initialiser;

import org.jgameengine.engine.Engine;
import org.jgameengine.engine.EngineInitialiser;
import org.jgameengine.renderer.lwjgl.LWJGLTextureCache;
import org.jgameengine.testgame.gameobjects.SpawnListener;

/**
 * Adds the initial game objects that are appropriate for the client side of the testgame.
 * <p/>
 * User: Owen Butler
 * Date: 9/04/2005
 * Time: 01:42:03
 */
public class TestGameClientInitialiser
        implements EngineInitialiser {

    private Engine engine;

    /**
     * called by the engine before the first frame.
     * <p/>
     * This is the spot where you would insert your custom game objects.
     */
    public void initialiseGame() {

        // add our client side initial game objects

        // in this case, a mouse listener (which spawns the player among other things)
        engine.getInputManager().addMouseListener(new SpawnListener(engine));

        // lets start some music too
//        StreamHandle makeMusicGoNow = engine.getAudioManager().loadMusicInMemory("audio/music/a-space-between-things-corri.mp3");
//        makeMusicGoNow.playStream();
    }

    /**
     * Load any sounds before the first gameframe is ticked.
     */
    public void soundPreLoad() {

    }

    /**
     * Load any graphics before the first frame is rendered.
     */
    public void graphicsPreLoad() {
        LWJGLTextureCache.getTextureId("clouds/clouds.png");
        LWJGLTextureCache.getTextureId("playerBullet.png");
        LWJGLTextureCache.getTextureId("ship1.png");
        LWJGLTextureCache.getTextureId("spark/spark_01.png");
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
