package org.owenbutler.grazier.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.opengl.TextureImpl;

public class HudManager extends HudManagerSupport {

    protected boolean showGameOver;

    public void doRenderHud() {

        if (showingIntroScreen) {
            TextureImpl.bindNone();

            trueTypeFont.drawString(100, 100, "Press mouse 1 to start");
            trueTypeFont.drawString(120, 120, "wasd to move");
            trueTypeFont.drawString(120, 140, "mouse 1 to fire");
            trueTypeFont.drawString(120, 160, "mouse 2 to rainbow attack");
            trueTypeFont.drawString(120, 180, "scrape 10 bullets for a rainbow attack");
        }

        if (showGameOver) {
            // TextureImpl.bindNone();

        }
    }

    public boolean isShowGameOver() {
        return showGameOver;
    }

    public void setShowGameOver(boolean showGameOver) {
        this.showGameOver = showGameOver;
    }
}
