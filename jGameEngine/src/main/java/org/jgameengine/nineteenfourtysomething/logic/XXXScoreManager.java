package org.jgameengine.nineteenfourtysomething.logic;

import org.jgameengine.common.score.DefaultScoreManager;

public class XXXScoreManager
        extends DefaultScoreManager {

    public void initScore() {
        super.initScore();
    }

    protected void updateDrawables() {

        NineHudManager hudManager = (NineHudManager) engine.getRegisteredGameService("hudManager");

        hudManager.setCurrentScore(score);
    }
}
