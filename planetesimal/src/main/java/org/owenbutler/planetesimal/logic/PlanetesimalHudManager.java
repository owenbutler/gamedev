package org.owenbutler.planetesimal.logic;

import org.jgameengine.renderer.HudManagerSupport;
import org.newdawn.slick.opengl.TextureImpl;

import java.util.List;

public class PlanetesimalHudManager
        extends HudManagerSupport {

    protected boolean showIntroScreen;

    protected int score;

    protected String stScore;

    protected List<Integer> highScores;

    protected boolean showScore;

    public void doRenderHud() {

        if (showIntroScreen) {
            TextureImpl.bindNone();

            drawIntroScreen();

        } else if (showScore) {

            TextureImpl.bindNone();
            trueTypeFont.drawString(10, 10, stScore);
        }
    }

    private void drawIntroScreen() {

        trueTypeFont.drawString(50, 50, "Welcome to planetesimal");
        trueTypeFont.drawString(50, 100, "Press fire to start");
        trueTypeFont.drawString(50, 160, "Controls:");
        trueTypeFont.drawString(80, 190, "Mouse to aim and shoot");
        trueTypeFont.drawString(80, 210, "esdf to move around");

        if (highScores != null) {
            if (highScores.size() > 0) {
                trueTypeFont.drawString(50, 300, "High Scores:");

                int yPos = 340;
                for (Integer highScore : highScores) {

                    trueTypeFont.drawString(90, yPos, Integer.toString(highScore));
                    yPos += 20;
                }
            }
        }
    }

    public void showScore() {
        showScore = true;
    }

    public void hideScore() {
        showScore = false;
    }

    public void showIntroScreen() {
        showIntroScreen = true;
    }

    public void hideIntroScreen() {
        showIntroScreen = false;
    }

    public void updateScore(int score) {

        this.score = score;
        stScore = Integer.toString(this.score);
    }

    public void updateHighScores(List<Integer> highScores) {
        this.highScores = highScores;
    }
}
