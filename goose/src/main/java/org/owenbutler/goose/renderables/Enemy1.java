package org.owenbutler.goose.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.logic.GameLogic;

public class Enemy1 extends BaseDrawableGameObject {

    protected GameLogic game;

    public Enemy1(float x, float y) {
        super(AssetConstants.gfx_enemy1, x, y, 32, 32);

        setCollisionRadius(16);

        setCollidable(true);

        setSortZ(AssetConstants.Z_ENEMY);

        velX = 200;

        game = (GameLogic) gameEngine.getRegisteredGameService("game");
    }

    public void think() {
        baseDrawableThink();

        if (x > 750) {
            velX = -200;
        } else if (x < 50) {
            velX = 200;
        }

        game.updateEnemyPosition(x);
    }

    public void collision(Collidable otherBody) {
//        if (otherBody instanceof Player || otherBody instanceof Grazer) {
//            removeSelf();
//        }
    }

}