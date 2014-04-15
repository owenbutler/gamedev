package org.owenbutler.warpball.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.TextureImpl;

public class WarpballHudManager
        extends HudManagerSupport {

    private int playerScore;
    private int enemyScore;

    private boolean renderHud = true;

    private boolean showingIntroScreen;

    /**
     * Render the HUD for this frame.
     */
    public void doRenderHud() {

        if (renderHud) {

            TextureImpl.bindNone();

            trueTypeFont.drawString(300, 20, Integer.toString(playerScore), Color.white);
            trueTypeFont.drawString(460, 20, Integer.toString(enemyScore), Color.white);
        }

        if (showingIntroScreen) {
            showIntroScreen();
        }
    }

    public void updateScores(int player, int enemy) {
        playerScore = player;
        enemyScore = enemy;
    }

    public void showIntroScreen() {

        showingIntroScreen = true;

        trueTypeFont.drawString(100, 100, "WARPBALL");
        trueTypeFont.drawString(100, 200, "MOUSE1 TO START");
        trueTypeFont.drawString(100, 300, "CONTROL WITH");
        trueTypeFont.drawString(100, 400, "MOUSE");
    }

    public void hideIntroScreen() {
        showingIntroScreen = false;
    }
}
