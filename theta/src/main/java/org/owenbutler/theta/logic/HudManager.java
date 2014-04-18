package org.owenbutler.theta.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public class HudManager extends HudManagerSupport {

    protected boolean showGameOver;

    protected boolean showLevelIntro;
    protected String levelIntro;

    protected boolean showLevelText;
    protected String levelText;

    protected boolean showLife;
    protected String lifeText;

    protected Color levelColor = new Color(64, 109, 106);
//    protected Color levelColor = new Color(120, 186, 176);

    protected Color levelTextColor = new Color(64, 109, 106);

    public HudManager() {
        setDefaultColor(levelTextColor);
    }

    public void doRenderHud() {

        if (showingIntroScreen) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(125, 215, "press mouse1 to start", levelColor);
        }

        if (showLevelIntro) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(10, 450, levelIntro, levelColor);
        }

        if (showGameOver) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(220, 215, "GAME OVER", levelColor);
        }

        if (showLevelText) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(240, 10, levelText, levelTextColor);
        }

        if (showLife) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(610, 450, lifeText, levelColor);
        }
    }

    public boolean isShowGameOver() {
        return showGameOver;
    }

    public void setShowGameOver(boolean showGameOver) {
        this.showGameOver = showGameOver;
    }

    public boolean isShowLevelIntro() {
        return showLevelIntro;
    }

    public void setShowLevelIntro(boolean showLevelIntro) {
        this.showLevelIntro = showLevelIntro;
    }

    public String getLevelIntro() {
        return levelIntro;
    }

    public void setLevelIntro(String levelIntro) {
        this.levelIntro = levelIntro;
    }

    public boolean isShowLevelText() {
        return showLevelText;
    }

    public void setShowLevelText(boolean showLevelText) {
        this.showLevelText = showLevelText;
    }

    public String getLevelText() {
        return levelText;
    }

    public void setLevelText(String levelText) {
        this.levelText = levelText;
    }

    public boolean isShowLife() {
        return showLife;
    }

    public void setShowLife(boolean showLife) {
        this.showLife = showLife;
    }

    public String getLifeText() {
        return lifeText;
    }

    public void setLifeText(String lifeText) {
        this.lifeText = lifeText;
    }

}
