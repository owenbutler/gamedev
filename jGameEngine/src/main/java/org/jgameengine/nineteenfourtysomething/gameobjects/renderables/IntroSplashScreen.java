package org.jgameengine.nineteenfourtysomething.gameobjects.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.nineteenfourtysomething.initialiser.AssetConstants;

/**
 * The guy that gets splashed up while waiting for the game to start.
 *
 * @author Owen Butler
 */
public class IntroSplashScreen
        extends BaseDrawableGameObject {

    /**
     * create a new player bullet.
     */
    public IntroSplashScreen() {
        super(AssetConstants.gfx_introScreen, 320, 240, 480, 480);
    }


    /**
     * Run a frame of think time.
     */
    public void think() {
    }
}

