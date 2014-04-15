package org.jgameengine.renderer;

import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Set;

abstract public class HudManagerSupport implements HudManager {

    protected TrueTypeFont trueTypeFont;

    protected Engine engine;

    protected String fontName;

    protected int fontSize;

    protected boolean showingIntroScreen;

    protected Color defaultColor;

    protected Set<HudCallback> hudCallbacks = new HashSet<HudCallback>();

    public void init() {
        try {
            Font startFont = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(HudManagerSupport.class.getClassLoader().getResourceAsStream(getFontName())));
            Font baseFont = startFont.deriveFont(Font.PLAIN, getFontSize());
            trueTypeFont = new TrueTypeFont(baseFont, true);

        } catch (Exception e) {
            throw new InitialisationException("error loading font: " + getFontName(), e);
        }
    }

    public void renderHud() {

        doRenderHud();

        for (HudCallback hudCallback : hudCallbacks) {
            hudCallback.render();
        }
    }

    protected abstract void doRenderHud();

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void showIntroScreen() {
        showingIntroScreen = true;
    }

    public void hideIntroScreen() {
        showingIntroScreen = false;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void drawTextAt(int x, int y, String text) {
        drawTextAt(x, y, text, defaultColor);
    }

    public void drawTextAt(int x, int y, String text, Color textColor) {
        TextureImpl.bindNone();
        trueTypeFont.drawString(x, y, text, textColor);
    }

    public void registerHudCallback(HudCallback hudCallback) {
        hudCallbacks.add(hudCallback);
    }

    public void deRegisterHudCallback(HudCallback hudCallback) {
        hudCallbacks.remove(hudCallback);
    }
}
