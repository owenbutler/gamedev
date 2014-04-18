package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

public class IntroSplashScreen
        extends BaseDrawableGameObject {

    public IntroSplashScreen() {
        super(AssetConstants.gfx_introScreen, 320, 240, 480, 480);
    }

    public void think() {
    }
}

