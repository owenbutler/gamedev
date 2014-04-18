package org.jgameengine.nineteenfourtysomething.logic;

import org.apache.log4j.Logger;
import org.jgameengine.common.InitialisationException;
import org.jgameengine.engine.Engine;
import org.jgameengine.renderer.HudCallback;
import org.jgameengine.renderer.HudManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import java.awt.*;
import java.io.BufferedInputStream;
import java.util.List;

public class NineHudManager
        implements HudManager {

    private static final Logger logger = Logger.getLogger(NineHudManager.class.getName());

    private TrueTypeFont trueTypeFont;

    private Engine engine;

    private String fontName;

    private int fontSize;

    private boolean renderCurrentScore;

    private boolean renderHighScores;

    private int currentScore;

    private String currentScoreString = "12341234";

    public void init() {
        logger.debug("Hud manager init fonts");
        try {
            Font startFont = Font.createFont(Font.TRUETYPE_FONT, new BufferedInputStream(NineHudManager.class.getClassLoader().getResourceAsStream(getFontName())));
            Font baseFont = startFont.deriveFont(Font.PLAIN, getFontSize());
            trueTypeFont = new TrueTypeFont(baseFont, true);

        } catch (Exception e) {
            throw new InitialisationException("error loading font", e);
        }

        engine.registerGameService("hudManager", this);
    }

    public void renderHud() {
        TextureImpl.bindNone();

        if (renderCurrentScore) {
            trueTypeFont.drawString(5, 460, currentScoreString, Color.red);
        }
        if (renderHighScores) {
            DefaultGameFlowLogic gameFlow = (DefaultGameFlowLogic) engine.getRegisteredGameService("gameFlowLogic");

            List<HighScore> highScores = gameFlow.getHighScores();

            trueTypeFont.drawString(50, 20, "- HIGH SCORES -", Color.red);

            int height = 40;
            int heightBetweenNameAndScore = 15;
            int heightMod = 40;
            for (HighScore highScore : highScores) {
                trueTypeFont.drawString(70, height, highScore.getName(), Color.darkGray);
                trueTypeFont.drawString(80, height + heightBetweenNameAndScore, highScore.getScoreString(), Color.darkGray);

                height += heightMod;
            }
        }

//        trueTypeFont.drawString(20, 20, "this is a test asdfasdf", org.newdawn.slick.Color.green);
    }

    public void currentScoreMode() {
        renderCurrentScore = true;
        renderHighScores = false;
    }

    public void highScoreMode() {
        renderCurrentScore = false;
        renderHighScores = true;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        currentScoreString = Integer.toString(currentScore);
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public String getCurrentScoreString() {
        return currentScoreString;
    }

    public void setCurrentScoreString(String currentScoreString) {
        this.currentScoreString = currentScoreString;
    }

    public void registerHudCallback(HudCallback hudCallback) {

    }

    public void deRegisterHudCallback(HudCallback hudCallback) {

    }
}
