package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.apache.commons.lang.math.RandomUtils;
import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.score.GenericScore;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * The score thing that pops out when you score.
 *
 * @author Owen Butler
 */
public class Score
        extends BaseDrawableGameObject implements GenericScore {

    public static final int SCORE_10 = 0;
    public static final int SCORE_25 = 1;
    public static final int SCORE_50 = 2;
    public static final int SCORE_75 = 3;
    public static final int SCORE_100 = 4;
    public static final int SCORE_150 = 5;
    public static final int SCORE_250 = 6;
    public static final int SCORE_350 = 7;
    public static final int SCORE_500 = 8;
    public static final int SCORE_800 = 9;
    public static final int SCORE_1k = 10;
    public static final int SCORE_2k = 11;
    public static final int SCORE_5k = 12;

    private int scoreType;

    private int[] typeToPoints = new int[] {10, 25, 50, 75, 100, 150, 250, 350, 500, 800, 1000, 2000, 5000};
    /**
     * create a new score
     *
     * @param x    x position
     * @param y    y position
     * @param type type of coin
     */
    public Score(float x, float y, int type) {
        super(AssetConstants.gfx_scores, x, y, 24, 24);
        setScreenClipRemove(false);

        getSurface().enableAnimation(null, 4, 4);

        getSurface().selectAnimationFrame(type);

        // start fading out in a second, fade for a bit then remove
        setFadeAndRemove(1.0f, 0.6f);

        setVelY(-20.0f);
        setVelX(RandomUtils.nextInt(20) - 10);
        scoreType = type;
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();
    }


    /**
     * Get the amount of points this score is worth.
     *
     * @return the amount of points.
     */
    public int getPoints() {
        return typeToPoints[scoreType];
    }
}