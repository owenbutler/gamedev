package org.jgameengine.nineteenfourtysomething.gameobjects.renderables.enemy;

import org.jgameengine.nineteenfourtysomething.gameobjects.renderables.common.TransExplosion;

public class MediumEnemy extends BaseEnemy {

    protected void enemyThink() {

    }

    protected void explode() {
//        MediumExplosion exp = new MediumExplosion(x, y);
//        gameEngine.addGameObject(exp);

        TransExplosion transExplosion = new TransExplosion(x, y);
        transExplosion.setScale(20, 20);

        gameEngine.addGameObject(transExplosion);

        spawnDebri();
    }

}
