package org.owenbutler.bcatch.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.opengl.TextureImpl;

import java.util.List;

public class HudManager extends HudManagerSupport {

    protected boolean showGameOver;

    String score = "0";

    /**
     * Render the HUD for this frame.
     */
    public void doRenderHud() {

        if (showingIntroScreen) {
            TextureImpl.bindNone();
            trueTypeFont.drawString(100, 100, "Mouse 1 to begin");
            trueTypeFont.drawString(120, 120, "left - s");
            trueTypeFont.drawString(120, 140, "right - f");
            trueTypeFont.drawString(120, 160, "up - e");
            trueTypeFont.drawString(120, 180, "down - d");
            trueTypeFont.drawString(100, 220, "Press Mouse1 to capture bullets");
            trueTypeFont.drawString(100, 240, "release Mouse1 to release captured bullets");

            if (scores != null) {

                trueTypeFont.drawString(100, 280, "highscores");

                int offsets = 0;
                for (Integer integer : scores) {
                    trueTypeFont.drawString(100, 300 + offsets, integer.toString());
                    offsets += 15;
                }
            }

        } else if (showGameOver) {
//            TextureImpl.bindNone();

        } else {
            TextureImpl.bindNone();
            trueTypeFont.drawString(5, 580, score);
        }
    }

    public boolean isShowGameOver() {
        return showGameOver;
    }

    public void setShowGameOver(boolean showGameOver) {
        this.showGameOver = showGameOver;
    }

    public void updateScore(int totalScore) {
        score = Integer.toString(totalScore);
    }

    protected List<Integer> scores;

    public void setHighScores(List<Integer> highScores) {

        scores = highScores;
    }
}
