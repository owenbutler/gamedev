package org.owenbutler.invasion.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.opengl.TextureImpl;

public class InvasionHudManager extends HudManagerSupport {

    protected boolean showGameOver;

    /**
     * Render the HUD for this frame.
     */
    public void doRenderHud() {

        if (showingIntroScreen) {
            TextureImpl.bindNone();
            trueTypeFont.drawString(100, 100, "Invasion:");
            trueTypeFont.drawString(120, 120, "left - e");
            trueTypeFont.drawString(120, 140, "right - f");
            trueTypeFont.drawString(120, 160, "fire - mouse1");
            trueTypeFont.drawString(120, 180, "fire to start");
            trueTypeFont.drawString(100, 220, "protect the planet from the invasion!");
            trueTypeFont.drawString(100, 240, "Look out for falling invaders!!");
        }

        if (showGameOver) {
            TextureImpl.bindNone();
            trueTypeFont.drawString(400, 300, "game over...");
        }
    }

    public boolean isShowGameOver() {
        return showGameOver;
    }

    public void setShowGameOver(boolean showGameOver) {
        this.showGameOver = showGameOver;
    }
}
