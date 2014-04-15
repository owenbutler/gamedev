package org.jgameengine.engine;

import org.jgameengine.common.InitialisationException;
import org.jgameengine.renderer.lwjgl.LWJGL2dRenderer;
import org.jgameengine.renderer.lwjgl.LWJGLTextureCache;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class EngineInitialiserSupport
        implements EngineInitialiser {

    protected Map<String, Integer> preLoadSounds;

    protected List<String> preLoadGraphics;

    protected Engine engine;

    public void soundPreLoad() {

        Font font = new Font("Verdana", Font.PLAIN, 16);
        TrueTypeFont loadingFont = new TrueTypeFont(font, true);
        loadingFont.drawString(10, 10, "please wait, loading...", Color.darkGray);
        Display.update();

        Display.setTitle("loading sounds");
        Set<Map.Entry<String, Integer>> sounds = preLoadSounds.entrySet();

        for (Map.Entry<String, Integer> sound : sounds) {
            try {
                engine.getAudioManager().loadOrGetSample(sound.getKey(), sound.getValue());
            } catch (NullPointerException e) {
                throw new InitialisationException("error preloading sound, " + sound.getKey(), e);
            }
        }
    }

    public void graphicsPreLoad() {
        Display.setTitle("loading gfx");
        for (String graphic : preLoadGraphics) {
            LWJGLTextureCache.getTextureId(graphic);
        }

        Display.setTitle(((LWJGL2dRenderer) engine.getRenderer()).getGameName());
    }

    public Map<String, Integer> getPreLoadSounds() {
        return preLoadSounds;
    }

    public void setPreLoadSounds(Map<String, Integer> preLoadSounds) {
        this.preLoadSounds = preLoadSounds;
    }

    public List<String> getPreLoadGraphics() {
        return preLoadGraphics;
    }

    public void setPreLoadGraphics(List<String> preLoadGraphics) {
        this.preLoadGraphics = preLoadGraphics;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
