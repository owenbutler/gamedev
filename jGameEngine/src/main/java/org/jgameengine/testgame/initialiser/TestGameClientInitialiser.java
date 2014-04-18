package org.jgameengine.testgame.initialiser;

import org.jgameengine.engine.Engine;
import org.jgameengine.engine.EngineInitialiser;
import org.jgameengine.renderer.lwjgl.LWJGLTextureCache;
import org.jgameengine.testgame.gameobjects.SpawnListener;

public class TestGameClientInitialiser
        implements EngineInitialiser {

    private Engine engine;

    public void initialiseGame() {

        // add our client side initial game objects

        // in this case, a mouse listener (which spawns the player among other things)
        engine.getInputManager().addMouseListener(new SpawnListener(engine));

        // lets start some music too
//        StreamHandle makeMusicGoNow = engine.getAudioManager().loadMusicInMemory("audio/music/a-space-between-things-corri.mp3");
//        makeMusicGoNow.playStream();
    }

    public void soundPreLoad() {

    }

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
