package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;

public abstract class BaseDrawableGameObject194x
        extends BaseDrawableGameObject {

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
