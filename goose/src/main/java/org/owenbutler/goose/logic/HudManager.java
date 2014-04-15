package org.owenbutler.goose.logic;

import org.jgameengine.renderer.HudManagerSupport;

public class HudManager extends HudManagerSupport {

    protected boolean showGameOver;

    /**
     * Render the HUD for this frame.
     */
    public void doRenderHud() {

        if (showingIntroScreen) {
            // TextureImpl.bindNone();

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
