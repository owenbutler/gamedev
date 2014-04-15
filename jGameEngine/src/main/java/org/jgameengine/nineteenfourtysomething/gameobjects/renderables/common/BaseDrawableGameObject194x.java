package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

/**
 * BaseDrawable for 194x containing helper methods specific to the game.
 *
 * Currently only a spawn debri method, used by exploding things.
 *
 * @author Owen Butler
 */
public abstract class BaseDrawableGameObject194x
        extends BaseDrawableGameObject {

    /**
     * constructor.
     *
     * @param surfaceName image name
     * @param x           x position
     * @param y           y position
     * @param width       width of the gameobject
     * @param height      height of the gameobject
     */
    protected BaseDrawableGameObject194x(String surfaceName, float x, float y, float width, float height) {
        super(surfaceName, x, y, width, height);
    }


    protected BaseDrawableGameObject194x() {
    }


    protected void spawnDebri() {

        int numDebri = 10;
        for (int i = 0; i < numDebri; ++i) {
            SmallDebri smallDebri = new SmallDebri(x, y);
            smallDebri.setScale(-10, -10);
            gameEngine.addGameObject(smallDebri);

        }
    }
}
