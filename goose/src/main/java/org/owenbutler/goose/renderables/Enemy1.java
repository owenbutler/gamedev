package org.owenbutler.goose.renderables;

import org.jgameengine.common.gameobjects.BaseDrawableGameObject;
import org.jgameengine.common.gameobjects.Collidable;
import org.owenbutler.goose.constants.AssetConstants;
import org.owenbutler.goose.logic.GameLogic;

public class Enemy1 extends BaseDrawableGameObject {

    protected GameLogic game;

    /**
     * constructor.
     *
     * @param x x position
     * @param y y position
     */
    public Enemy1(float x, float y) {
        super(AssetConstants.gfx_enemy1, x, y, 32, 32);

        setCollisionRadius(16);

        setCollidable(true);

        setSortZ(AssetConstants.Z_ENEMY);

        velX = 200;

        game = (GameLogic) gameEngine.getRegisteredGameService("game");
    }

    /**
     * Run a frame of think time.
     */
    public void think() {
        baseDrawableThink();

        if (x > 750) {
            velX = -200;
        } else if (x < 50) {
            velX = 200;
        }

        game.updateEnemyPosition(x);
    }

    /**
     * Collision with another object.
     *
     * @param otherBody the object we collided with
     */
    public void collision(Collidable otherBody) {
//        if (otherBody instanceof Player || otherBody instanceof Grazer) {
//            removeSelf();
//        }
    }

}